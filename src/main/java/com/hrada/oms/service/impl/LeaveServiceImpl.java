package com.hrada.oms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.LeaveRepository;
import com.hrada.oms.model.common.User;
import com.hrada.oms.model.log.Leave;
import com.hrada.oms.service.LeaveService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shin on 2018/10/17.
 */
@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    LeaveRepository leaveRepository;

    @Autowired
    MessageUtil messageUtil;

    @Override
    public Leave save(Leave leave) {
        if(leave.getApplicant()==null){
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            leave.setApplicant(user.getPersonal());
            leave = leaveRepository.save(leave);

            String email = "liqiang@hrada.net,wangjl@hrada.net,elizabeth@hrada.net";
            String title = user.getName()+"的【请假申请】需要审批";
            String content = "<a href='http://sys.hrada.net/leave/"+leave.getId()+"'>"+user.getName()+"的【请假申请】需要审批</a>";
            messageUtil.send(email, title, content);
        }else{
            leave = leaveRepository.save(leave);
        }
        return leave;
    }

    @Override
    public void pass(Long id) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Leave leave = leaveRepository.findOne(id);
        leave.setState(1);
        leave.setApprover(user.getPersonal());
        leaveRepository.save(leave);

        String email = leave.getApplicant().getEmail();
        String title = "【请假申请】审批通过";
        String content = "<a href='http://sys.hrada.net/leave/"+leave.getId()+"'>您的【请假申请】审批通过</a>";
        messageUtil.send(email, title, content);
    }

    @Override
    public void refuse(Long id, String reply) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Leave leave = leaveRepository.findOne(id);
        leave.setState(2);
        leave.setReply(reply);
        leave.setApprover(user.getPersonal());
        leaveRepository.save(leave);

        String email = leave.getApplicant().getEmail();
        String title = "【请假申请】审批未通过";
        String content = "<a href='http://sys.hrada.net/leave/"+leave.getId()+"'>您的【请假申请】审批未通过</a>";
        messageUtil.send(email, title, content);
    }

    @Override
    public JSONObject data(Integer pageNumber, Integer pageSize, final Long applicant, final Integer month) {
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        Page<Leave> page;
        if(SecurityUtils.getSubject().isPermitted("view_all_leave")){
            Specification specification = new Specification<Leave>() {
                @Override
                public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    if(null != applicant){
                        predicates.add(criteriaBuilder.equal(root.get("applicant"), applicant));
                    }
                    if(null != month){
                        predicates.add(criteriaBuilder.or(criteriaBuilder.between(root.get("startDate"), monthFirstDay(month), monthLastDay(month)), criteriaBuilder.between(root.get("endDate"), monthFirstDay(month), monthLastDay(month))));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };
            page = leaveRepository.findAll(specification, pageable);
        }else{
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            page = leaveRepository.findAllByApplicant(pageable, user.getPersonal());
        }
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }

    public Date monthFirstDay(Integer month){
        LocalDate today = LocalDate.now().withMonth(month);
        today = LocalDate.of(today.getYear(),today.getMonth(),1);
        return Date.from(today.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public Date monthLastDay(Integer month){
        LocalDate today = LocalDate.now().withMonth(month);
        today = today.with(TemporalAdjusters.lastDayOfMonth());
        return Date.from(today.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

}
