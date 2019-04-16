package com.hrada.oms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.SealRepository;
import com.hrada.oms.model.common.User;
import com.hrada.oms.model.log.Seal;
import com.hrada.oms.service.SealService;
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
 * Created by shin on 2018/10/19.
 */
@Service
public class SealServiceImpl implements SealService {

    @Autowired
    SealRepository sealRepository;

    @Autowired
    MessageUtil messageUtil;

    @Override
    public Seal save(Seal seal) {
        if(seal.getApplicant()==null){
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            seal.setApplicant(user.getPersonal());
            seal = sealRepository.save(seal);

            String email = "wangjl@hrada.net";
            String title = user.getName()+"的【签章使用】需要审批";
            String content = "<a href='http://sys.hrada.net/seal/"+seal.getId()+"'>"+user.getName()+"的【签章使用】需要审批</a>";
            messageUtil.send(email, title, content);
        }else{
            seal = sealRepository.save(seal);
        }
        return seal;
    }

    @Override
    public void pass(Long id) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Seal seal = sealRepository.findOne(id);
        seal.setState(1);
        seal.setApprover(user.getPersonal());
        sealRepository.save(seal);

        String email = seal.getApplicant().getEmail();
        String title = "【签章使用】审批通过";
        String content = "<a href='http://sys.hrada.net/seal/"+seal.getId()+"'>您的【签章使用】审批通过</a>";
        messageUtil.send(email, title, content);
    }

    @Override
    public void refuse(Long id) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Seal seal = sealRepository.findOne(id);
        seal.setState(2);
        seal.setApprover(user.getPersonal());
        sealRepository.save(seal);

        String email = seal.getApplicant().getEmail();
        String title = "【签章使用】审批未通过";
        String content = "<a href='http://sys.hrada.net/seal/"+seal.getId()+"'>您的【签章使用】审批未通过</a>";
        messageUtil.send(email, title, content);
    }

    @Override
    public JSONObject data(Integer pageNumber, Integer pageSize, final Date sDate, final Date eDate) {
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        Page<Seal> page;
        if(SecurityUtils.getSubject().hasRole("super")){
            Specification specification = new Specification<Seal>() {
                @Override
                public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    if(sDate!=null){
                        predicates.add(criteriaBuilder.greaterThan(root.get("logDate"), sDate));
                    }
                    if(eDate!=null){
                        predicates.add(criteriaBuilder.lessThan(root.get("logDate"), eDate));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };
            page = sealRepository.findAll(specification, pageable);
        }else{
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            page = sealRepository.findAllByApplicant(pageable, user.getPersonal());
        }
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
