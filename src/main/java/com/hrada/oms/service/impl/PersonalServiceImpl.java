package com.hrada.oms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.common.RoleRepository;
import com.hrada.oms.dao.common.UserRepository;
import com.hrada.oms.dao.model.PersonalRepository;
import com.hrada.oms.model.common.Role;
import com.hrada.oms.model.common.User;
import com.hrada.oms.model.model.Personal;
import com.hrada.oms.service.PersonalService;
import com.hrada.oms.util.DateUtil;
import com.hrada.oms.util.LunarUtil;
import com.hrada.oms.util.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shin on 2018/5/10.
 */
@Service
public class PersonalServiceImpl implements PersonalService {

    @Autowired
    PersonalRepository personalRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MD5Util md5Util;

    @Autowired
    LunarUtil lunarUtil;

    @Autowired
    DateUtil dateUtil;

    @Override
    public List<Personal> findSeller() {
        List<Personal> list = new ArrayList<>();
        Role role = roleRepository.findByName("销售");
        List<User> users = userRepository.findAllByRoleList(role);
        for(User user:users){
            list.add(user.getPersonal());
        }
        return list;
    }

    @Override
    public JSONArray birthday() {
        JSONArray array = new JSONArray();
        List<Personal> list = personalRepository.findAll();
        for(Personal personal:list){
            if(personal.getBirthdayType()==0){
                String today = dateUtil.convert2String(new Date(), "yyyy年MM月dd日");
                if(lunarUtil.getLunar(today).equals(personal.getBirthday())){
                    JSONObject object = new JSONObject();
                    object.put("id", personal.getId());
                    object.put("name", personal.getName());
                    array.add(object);
                }
            }else{
                String today = dateUtil.convert2String(new Date(), "M-d");
                if(personal.getBirthday().equals(today)){
                    JSONObject object = new JSONObject();
                    object.put("id", personal.getId());
                    object.put("name", personal.getName());
                    array.add(object);
                }
            }
        }
        return array;
    }

    @Override
    public Personal save(Personal personal) {
        User user = personal.getUser();
        if(user == null){
            user = new User();
            user.setName(personal.getName());
            user.setUname(personal.getEmail());
            user.setUpass(md5Util.toMd5("123456","",1024));
            user.setState(1);
            user.setPersonal(personal);
            personal.setUser(user);
        }
        if(personal.getYy()!=null && personal.getMm()!=null && personal.getDd()!=null) {
            if (personal.getBirthdayType( ) == 0) {
                personal.setBirthday(lunarUtil.getLunar(personal.getYy( ) + "年" + personal.getMm( ) + "月" + personal.getDd( ) + "日"));
            } else {
                personal.setBirthday(personal.getMm( ) + "-" + personal.getDd( ));
            }
        }
        return personalRepository.save(personal);
    }

    @Override
    public JSONObject data(Integer pageNumber, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNumber-1, pageSize);
        Page<Personal> page;
        if(SecurityUtils.getSubject().isPermitted("view_all_personal")){
            page = personalRepository.findAll(pageable);
        }else{
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            page = personalRepository.findAllByUser(pageable, user);
        }
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
