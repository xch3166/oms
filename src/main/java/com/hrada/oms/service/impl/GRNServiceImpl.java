package com.hrada.oms.service.impl;

import com.hrada.oms.dao.log.GRNRepository;
import com.hrada.oms.dao.log.InventoryRepository;
import com.hrada.oms.dao.model.ProductRepository;
import com.hrada.oms.model.log.Detail;
import com.hrada.oms.model.log.GRN;
import com.hrada.oms.model.log.Inventory;
import com.hrada.oms.model.model.Product;
import com.hrada.oms.service.GRNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shin on 2018/11/24.
 */
@Service
public class GRNServiceImpl implements GRNService {

    @Autowired
    GRNRepository grnRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Override
    public GRN save(GRN grn) {
        if(grn.getId()==null) {
            List<Inventory> inventories = new ArrayList<>();
            List<Detail> list = grn.getDetails( );
            for (Detail detail : list) {
                Product product = detail.getProduct();
                Inventory inventory = inventoryRepository.findByProduct(product);
                if(inventory==null){
                    inventory = new Inventory();
                    inventory.setProduct(product);
                }
                inventory.setAmount(inventory.getAmount() + detail.getAmount());
                inventory = inventoryRepository.save(inventory);
                inventories.add(inventory);
            }
            grn.setInventories(inventories);
        }
        return grnRepository.save(grn);
    }


}
