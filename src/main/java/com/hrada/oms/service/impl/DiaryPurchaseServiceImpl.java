package com.hrada.oms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.DiaryPurchaseRepository;
import com.hrada.oms.model.common.User;
import com.hrada.oms.model.log.DiaryPurchase;
import com.hrada.oms.model.log.Mail;
import com.hrada.oms.service.DiaryPurchaseService;
import com.hrada.oms.util.MailUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by shin on 2018/11/27.
 */
@Service
public class DiaryPurchaseServiceImpl implements DiaryPurchaseService {

    @Autowired
    DiaryPurchaseRepository diaryPurchaseRepository;

    @Autowired
    MailUtil mailUtil;

    @Override
    public DiaryPurchase save(DiaryPurchase diaryPurchase) {
        if(diaryPurchase.getApplicant()==null){
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            diaryPurchase.setApplicant(user.getPersonal());

            Mail mail = new Mail();
            mail.setReceiver("liqiang@hrada.net,wangjl@hrada.net");
            mail.setSubject(user.getName()+"的【日常采购】需要审批");
            mail.setContent("http://sys.hrada.net/diaryPurchase/list");
            mailUtil.sendEmail(mail);
        }
        diaryPurchaseRepository.save(diaryPurchase);
        return diaryPurchase;
    }

    @Override
    public void pass(Long id) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        DiaryPurchase diaryPurchase = diaryPurchaseRepository.findOne(id);
        diaryPurchase.setState(1);
        diaryPurchase.setApprover(user.getPersonal());
        diaryPurchaseRepository.save(diaryPurchase);

        Mail mail = new Mail();
        mail.setReceiver(diaryPurchase.getApplicant().getEmail());
        mail.setSubject("【日常采购】审批通过");
        mail.setContent("【日常采购】审批通过");
        mailUtil.sendEmail(mail);
    }

    @Override
    public void refuse(Long id) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        DiaryPurchase diaryPurchase = diaryPurchaseRepository.findOne(id);
        diaryPurchase.setState(2);
        diaryPurchase.setApprover(user.getPersonal());
        diaryPurchaseRepository.save(diaryPurchase);

        Mail mail = new Mail();
        mail.setReceiver(diaryPurchase.getApplicant().getEmail());
        mail.setSubject("【日常采购】审批未通过");
        mail.setContent("【日常采购】审批未通过");
        mailUtil.sendEmail(mail);
    }

    @Override
    public JSONObject data(Integer pageNumber, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        Page<DiaryPurchase> page;
        if(SecurityUtils.getSubject().isPermitted("view_all_diary_purchase")){
            page = diaryPurchaseRepository.findAll(pageable);
        }else{
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            page = diaryPurchaseRepository.findAllByApplicant(pageable, user.getPersonal());
        }
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
