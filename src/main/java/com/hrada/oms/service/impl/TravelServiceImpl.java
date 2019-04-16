package com.hrada.oms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.TravelRepository;
import com.hrada.oms.model.common.User;
import com.hrada.oms.model.log.Travel;
import com.hrada.oms.service.TravelService;
import com.hrada.oms.util.MessageUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by shin on 2018/10/24.
 */
@Service
public class TravelServiceImpl implements TravelService {

    @Autowired
    TravelRepository travelRepository;

    @Autowired
    MessageUtil messageUtil;

    @Override
    public Travel save(Travel travel) {
        if(travel.getId()==null){
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            travel.setApplicant(user.getPersonal());
            travel = travelRepository.save(travel);

            String email = "liqiang@hrada.net,wangjl@hrada.net";
            String title = user.getName()+"的【出差申请】需要审批";
            String content = "<a href='http://sys.hrada.net/travel/"+travel.getId()+"'>"+user.getName()+"的【出差申请】需要审批</a>";
            messageUtil.send(email, title, content);
        }else{
            travel = travelRepository.save(travel);
        }
        return travel;
    }

    @Override
    public void pass(Long id) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Travel travel = travelRepository.findOne(id);
        travel.setState(2);
        travel.setHrApprover(user.getPersonal());
        travel.setHrDate(new Date());
        travelRepository.save(travel);

        String email = travel.getApplicant().getEmail();
        String title = "您的【出差申请】审批通过";
        String content = "<a href='http://sys.hrada.net/travel/"+travel.getId()+"'>您的【出差申请】审批通过</a>";
        messageUtil.send(email, title, content);
    }

    @Override
    public void refuse(Long id) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Travel travel = travelRepository.findOne(id);
        travel.setHrApprover(user.getPersonal());
        travel.setHrDate(new Date());
        travel.setState(3);
        travelRepository.save(travel);

        String email = travel.getApplicant().getEmail();
        String title = "您的【出差申请】审批未通过";
        String content = "<a href='http://sys.hrada.net/travel/"+travel.getId()+"'>您的【出差申请】审批未通过</a>";
        messageUtil.send(email, title, content);
    }

    @Override
    public JSONObject data(Integer pageNumber, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        Page<Travel> page;
        if(SecurityUtils.getSubject().hasRole("super")){
            page = travelRepository.findAll(pageable);
        }else{
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            page = travelRepository.findAllByApplicant(pageable, user.getPersonal());
        }
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
