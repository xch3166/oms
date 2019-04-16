package com.hrada.oms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.PaymentRepository;
import com.hrada.oms.model.common.User;
import com.hrada.oms.model.log.Payment;
import com.hrada.oms.service.PaymentService;
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
import java.util.Date;
import java.util.List;

/**
 * Created by shin on 2018/10/17.
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    MessageUtil messageUtil;

    @Override
    public JSONObject getPieData(Integer year) {
        JSONObject object = new JSONObject();
        object.put("purchaseRMB", paymentRepository.getPurchaseRMBByYear(year));
        object.put("purchaseUSD", paymentRepository.getPurchaseUSDByYear(year));
        object.put("reimburse", paymentRepository.getReimburseByYear(year));
        return object;
    }

    @Override
    public JSONObject getLineData(Integer year) {
        JSONObject object = new JSONObject();
        Double[] purchaseRMB = new Double[12];
        Double[] purchaseUSD = new Double[12];
        Double[] reimburse = new Double[12];
        for(int i=0;i<12;i++){
            purchaseRMB[i] = paymentRepository.getPurchaseRMBByMonth(year, i+1);
            purchaseUSD[i] = paymentRepository.getPurchaseUSDByMonth(year, i+1);
            reimburse[i] = paymentRepository.getReimburseByMonth(year, i+1);
        }
        object.put("purchaseRMB", purchaseRMB);
        object.put("purchaseUSD", purchaseUSD);
        object.put("reimburse", reimburse);
        return object;
    }

    @Override
    public Payment save(Payment payment) {
        if(payment.getApplicant()==null){
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            payment.setApplicant(user.getPersonal());
            payment = paymentRepository.save(payment);

            String email = "lilu@hrada.net";
            String title = user.getName()+"的【付款审批单】需要审批";
            String content = "<a href='http://sys.hrada.net/payment/"+payment.getId()+"'>"+user.getName()+" 提交 "+payment.getReceipt()+"的【付款审批单】需要审批 </a>";
            messageUtil.send(email, title, content);
        }else{
            payment = paymentRepository.save(payment);
        }

        return payment;
    }

    @Override
    public void reSub(Long id) {
        Payment payment = paymentRepository.findOne(id);
        String email = "";
        switch (payment.getState()){
            case 4:
                payment.setState(0);
                email="lilu@hrada.net";
                break;
            case 5:
                payment.setState(1);
                if(payment.getCompany().getId()==16){
                    email = "liqiang@hrada.net";
                }else if(payment.getCompany().getId()==17){
                    email = "elizabeth@hrada.net";
                }
                break;
            case 6:
                payment.setState(2);
                email="wangjl@hrada.net";
                break;
        }
        paymentRepository.save(payment);

        String title = payment.getApplicant().getName()+"的【付款审批单】需要审批";
        String content = "<a href='http://sys.hrada.net/payment/"+payment.getId()+"'>"+payment.getApplicant().getName()+" 提交 "+payment.getReceipt()+"的【付款审批单】需要审批 </a>";
        messageUtil.send(email, title, content);
    }

    @Override
    public void pass(Long id) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Payment payment = paymentRepository.findOne(id);
        String manager = "", email1="";
        switch (payment.getState()){
            case 0:
                payment.setState(1);
                manager="会计";
                if(payment.getCompany().getId()==16){
                    email1 = "liqiang@hrada.net";
                }else if(payment.getCompany().getId()==17){
                    email1 = "elizabeth@hrada.net";
                }
                break;
            case 1:
                payment.setState(2);
                email1 = "wangjl@hrada.net";
                manager="负责人";
                break;
            case 2:
                payment.setState(3);
                manager="出纳";
                break;
        }
        payment.setApprover(user.getPersonal());
        paymentRepository.save(payment);

        String email = payment.getApplicant().getEmail();
        String title = "【付款审批单】"+manager+"审批通过";
        String content = "<a href='http://sys.hrada.net/payment/"+payment.getId()+"'>【付款审批单】"+manager+"审批通过</a>";
        messageUtil.send(email, title, content);

        String title1 = payment.getApplicant().getName()+"的【付款审批单】需要审批";
        String content1 = "<a href='http://sys.hrada.net/payment/"+payment.getId()+"'>"+payment.getApplicant().getName()+" 提交 "+payment.getReceipt()+"的【付款审批单】需要审批 </a>";
        messageUtil.send(email1, title1, content1);
    }

    @Override
    public void refuse(Long id) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Payment payment = paymentRepository.findOne(id);
        String manager = "";
        switch (payment.getState()){
            case 0:
                payment.setState(4);
                manager="会计";
                break;
            case 1:
                payment.setState(5);
                manager="负责人";
                break;
            case 2:
                payment.setState(6);
                manager="出纳";
                break;
        }
        payment.setApprover(user.getPersonal());
        paymentRepository.save(payment);

        String email = payment.getApplicant().getEmail();
        String title = "【付款审批单】"+manager+"审批未通过";
        String content = "<a href='http://sys.hrada.net/payment/"+payment.getId()+"'>【付款审批单】"+manager+"审批未通过</a>";
        messageUtil.send(email, title, content);
    }

    @Override
    public JSONObject data(Integer pageNumber, Integer pageSize, final Date logDate, final String receipt, final Long applicant) {
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        Page<Payment> page;
        if(SecurityUtils.getSubject().hasRole("super") || SecurityUtils.getSubject().hasRole("会计")){
            Specification specification = new Specification<Payment>() {
                @Override
                public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    if(logDate!=null){
                        predicates.add(criteriaBuilder.equal(root.get("logDate"), logDate));
                    }
                    if(applicant!=null){
                        predicates.add(criteriaBuilder.equal(root.get("applicant"), applicant));
                    }
                    if(receipt!=null){
                        predicates.add(criteriaBuilder.like(root.get("receipt"), "%"+receipt+"%"));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };
            page = paymentRepository.findAll(specification, pageable);
        }else{
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            Specification specification = new Specification<Payment>() {
                @Override
                public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    predicates.add(criteriaBuilder.equal(root.get("applicant"), user.getPersonal()));
                    if(logDate!=null){
                        predicates.add(criteriaBuilder.equal(root.get("logDate"), logDate));
                    }
                    if(receipt!=null){
                        predicates.add(criteriaBuilder.like(root.get("receipt"), "%"+receipt+"%"));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };
            page = paymentRepository.findAll(specification, pageable);
        }
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
