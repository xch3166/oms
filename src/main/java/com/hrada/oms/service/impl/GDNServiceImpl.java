package com.hrada.oms.service.impl;

import com.hrada.oms.dao.log.GDNRepository;
import com.hrada.oms.dao.log.InventoryRepository;
import com.hrada.oms.dao.model.ProductRepository;
import com.hrada.oms.model.common.User;
import com.hrada.oms.model.log.Detail;
import com.hrada.oms.model.log.GDN;
import com.hrada.oms.model.log.Inventory;
import com.hrada.oms.model.model.Product;
import com.hrada.oms.service.GDNService;
import com.hrada.oms.util.MessageUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shin on 2018/11/24.
 */
@Service
public class GDNServiceImpl implements GDNService {

    @Autowired
    GDNRepository gdnRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    MessageUtil messageUtil;

    @Override
    public GDN save(GDN gdn) {
        if(gdn.getId()==null) {
            List<Inventory> inventories = new ArrayList<>();
            List<Detail> list = gdn.getDetails();
            for (Detail detail : list) {
                Product product = detail.getProduct();
                Inventory inventory = inventoryRepository.findByProduct(product);
                if(inventory==null){
                    inventory = new Inventory();
                    inventory.setProduct(product);
                }
                inventory.setAmount(inventory.getAmount() - detail.getAmount());
                inventory = inventoryRepository.save(inventory);
                inventories.add(inventory);
            }
            gdn.setInventories(inventories);
            gdn = gdnRepository.save(gdn);

            String email = "elizabeth@hrada.net";
            String title = gdn.getApplicant().getName()+"提交的【出库单】需要审批";
            String content = "<a href='http://sys.hrada.net/gdn/"+gdn.getId()+"'>"+gdn.getApplicant().getName()+" 提交的【出库单】需要审批 </a>";
            messageUtil.send(email, title, content);
        }else{
            gdn = gdnRepository.save(gdn);
        }
        return gdn;
    }

    @Override
    public void pass(Long id) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        GDN gdn = gdnRepository.findOne(id);
        String manager = "";
        switch (gdn.getState()){
            case 0:
                gdn.setState(1);
                manager="负责人";

                String email1 = "lilu@hrada.net";
                String title1 = gdn.getApplicant().getName()+"提交的【出库单】需要审批";
                String content1 = "<a href='http://sys.hrada.net/gdn/"+gdn.getId()+"'>"+gdn.getApplicant().getName()+" 提交的【出库单】需要审批 </a>";
                messageUtil.send(email1, title1, content1);
                break;
            case 1:
                gdn.setState(2);
                manager="会计";
                break;
        }
        gdn.setApprover(user.getPersonal());
        gdnRepository.save(gdn);

        String email = gdn.getApplicant().getEmail();
        String title = "【出库单】"+manager+"审批通过";
        String content = "<a href='http://sys.hrada.net/gdn/"+gdn.getId()+"'>【出库单】"+manager+"审批通过</a>";
        messageUtil.send(email, title, content);
    }

    @Override
    public void refuse(Long id) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        GDN gdn = gdnRepository.findOne(id);
        String manager = "";
        switch (gdn.getState()){
            case 0:
                gdn.setState(3);
                manager="负责人";
                break;
            case 1:
                gdn.setState(4);
                manager="会计";
                break;
        }
        gdn.setApprover(user.getPersonal());
        gdnRepository.save(gdn);

        String email = gdn.getApplicant().getEmail();
        String title = "【出库单】"+manager+"审批未通过";
        String content = "<a href='http://sys.hrada.net/gdn/"+gdn.getId()+"'>【出库单】"+manager+"审批未通过</a>";
        messageUtil.send(email, title, content);
    }

    @Override
    public void reSub(Long id) {
        GDN gdn = gdnRepository.findOne(id);
        String email = "";
        switch (gdn.getState()){
            case 3:
                gdn.setState(0);
                email="elizabeth@hrada.net";
                break;
            case 4:
                gdn.setState(1);
                email="lilu@hrada.net";
                break;
        }
        gdnRepository.save(gdn);

        String title = gdn.getApplicant().getName()+"提交的【出库单】需要审批";
        String content = "<a href='http://sys.hrada.net/gdn/"+gdn.getId()+"'>"+gdn.getApplicant().getName()+" 提交的【出库单】需要审批 </a>";
        messageUtil.send(email, title, content);
    }


}
