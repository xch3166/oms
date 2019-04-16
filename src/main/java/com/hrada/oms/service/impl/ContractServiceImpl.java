package com.hrada.oms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.ContractRepository;
import com.hrada.oms.model.common.User;
import com.hrada.oms.model.log.Contract;
import com.hrada.oms.model.log.ContractDetail;
import com.hrada.oms.service.ContractService;
import com.hrada.oms.util.MessageUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shin on 2019-02-15.
 */
@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    MessageUtil messageUtil;

    @Override
    public Contract save(Contract contract) {
        if(contract.getApplicant()==null){
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            contract.setApplicant(user.getPersonal());
        }

        String type = "销售";
        if(contract.getType()==0){
            type = "采购";
        }
        List<ContractDetail> details = contract.getDetails();
        List<ContractDetail> list = new ArrayList<>();
        for(ContractDetail detail:details){
            if(detail.getProduct()!=null){
                list.add(detail);
            }
        }
        contract.setDetails(list);
        contract = contractRepository.save(contract);

        details = contract.getDetails();
        for(ContractDetail detail:details){
            detail.setContract(contract);
        }
        contract.setDetails(details);

        String email = "elizabeth@hrada.net";
        String title = contract.getApplicant().getName()+"提交的【"+type+"合同】需要审批";
        String content = "<a href='http://sys.hrada.net/contract/sell/"+contract.getId()+"'>"+contract.getApplicant().getName()+" 提交的【"+type+"合同】需要审批 </a>";
        messageUtil.send(email, title, content);
        return contractRepository.save(contract);
    }

    @Override
    public void pass(Long id) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Contract contract = contractRepository.findOne(id);
        String type = "销售";
        if(contract.getType()==0){
            type = "采购";
        }
        String manager = "";
        switch (contract.getState()){
            case 0:
                contract.setState(1);
                manager="负责人";

                String email1 = "lilu@hrada.net";
                String title1 = contract.getApplicant().getName()+"提交的【"+type+"合同】需要审批";
                String content1 = "<a href='http://sys.hrada.net/contract/sell/"+contract.getId()+"'>"+contract.getApplicant().getName()+" 提交的【"+type+"合同】需要审批 </a>";
                messageUtil.send(email1, title1, content1);
                break;
            case 1:
                contract.setState(2);
                manager="会计";
                break;
        }
        contract.setApprover(user.getPersonal());
        contractRepository.save(contract);

        String email = contract.getApplicant().getEmail();
        String title = "【"+type+"合同】"+manager+"审批通过";
        String content = "<a href='http://sys.hrada.net/contract/sell/"+contract.getId()+"'>【"+type+"合同】"+manager+"审批通过</a>";
        messageUtil.send(email, title, content);
    }

    @Override
    public void refuse(Long id) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Contract contract = contractRepository.findOne(id);
        String type = "销售";
        if(contract.getType()==0){
            type = "采购";
        }
        String manager = "";
        switch (contract.getState()){
            case 0:
                contract.setState(3);
                manager="负责人";
                break;
            case 1:
                contract.setState(4);
                manager="会计";
                break;
        }
        contract.setApprover(user.getPersonal());
        contractRepository.save(contract);

        String email = contract.getApplicant().getEmail();
        String title = "【"+type+"合同】"+manager+"审批未通过";
        String content = "<a href='http://sys.hrada.net/contract/sell/"+contract.getId()+"'>【"+type+"合同】"+manager+"审批未通过</a>";
        messageUtil.send(email, title, content);
    }

    @Override
    public void reSub(Long id) {
        Contract contract = contractRepository.findOne(id);
        String type = "销售";
        if(contract.getType()==0){
            type = "采购";
        }
        String email = "";
        switch (contract.getState()){
            case 3:
                contract.setState(0);
                email="elizabeth@hrada.net";
                break;
            case 4:
                contract.setState(1);
                email="lilu@hrada.net";
                break;
        }
        contractRepository.save(contract);

        String title = contract.getApplicant().getName()+"提交的【"+type+"合同】需要审批";
        String content = "<a href='http://sys.hrada.net/contract/"+contract.getId()+"'>"+contract.getApplicant().getName()+" 提交的【"+type+"合同】需要审批 </a>";
        messageUtil.send(email, title, content);
    }

    @Override
    public JSONObject sellData(Integer pageNumber, Integer pageSize, Long seller, Long client) {
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC,"id");
        Specification specification = new Specification<Contract>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("type"), 1));
                if(!SecurityUtils.getSubject().isPermitted("view_all_sell")){
                    User user = (User) SecurityUtils.getSubject().getPrincipal();
                    predicates.add(criteriaBuilder.equal(root.get("createUser"), user.getId()));
                }
                if(client!=null){
                    predicates.add(criteriaBuilder.equal(root.get("client"), client));
                }
                if(seller!=null){
                    predicates.add(criteriaBuilder.equal(root.get("seller"), seller));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return getJsonObject(pageable, specification);
    }

    @Override
    public JSONObject purchaseData(Integer pageNumber, Integer pageSize, Long supplier) {
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");

        Specification specification = new Specification<Contract>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("type"), 0));
                if(!SecurityUtils.getSubject().isPermitted("view_all_purchase")){
                    User user = (User) SecurityUtils.getSubject().getPrincipal();
                    predicates.add(criteriaBuilder.equal(root.get("createUser"), user.getId()));
                }
                if(supplier!=null){
                    predicates.add(criteriaBuilder.equal(root.get("supplier"), supplier));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return getJsonObject(pageable, specification);
    }

    private JSONObject getJsonObject(Pageable pageable, Specification specification) {
        Page<Contract> page = contractRepository.findAll(specification, pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
