package com.hrada.oms.controller.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.common.RoleRepository;
import com.hrada.oms.dao.common.UserRepository;
import com.hrada.oms.model.common.Role;
import com.hrada.oms.model.common.User;
import com.hrada.oms.util.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shin on 2018/8/9.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MD5Util md5Util;

    @RequestMapping("/password")
    public String password(){
        return "/common/user/password";
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public String updatePassword(@RequestParam("newPass") String newPass, @RequestParam("oldPass") String oldPass){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user.getUpass().equals(md5Util.toMd5(oldPass,"",1024))){
            user.setUpass(md5Util.toMd5(newPass,"",1024));
            userRepository.save(user);
            return "ok";
        }else{
            return "error";
        }
    }

    @RequestMapping("/checkOldPass")
    @ResponseBody
    public Boolean checkOldPass(@RequestParam("oldPass") String oldPass){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user.getUpass().equals(md5Util.toMd5(oldPass,"",1024));
    }

    @RequestMapping("/resetPassword")
    @ResponseBody
    public String resetPassword(@RequestParam("ids") String ids){
        String[] idArray = ids.split(",");
        for(String id:idArray){
            User user = userRepository.findOne(new Long(id));
            user.setUpass(md5Util.toMd5("123456","",1024));
            userRepository.save(user);
        }
        return "ok";
    }

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute(new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "/common/user/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        model.addAttribute("user",userRepository.findAll());
        return "/common/user/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        User user = userRepository.findOne(id);
        List<Role> roles = roleRepository.findAll();
        Set<Role> list = user.getRoleList();
        if(list!=null && roles!=null){
            for(Role role:roles){
                for(Role r:list){
                    if(role.getId()==r.getId()){
                        role.setFlag(1);
                    }
                }
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "/common/user/detail";
    }

    @PostMapping("/save")
    public String save(User e, @RequestParam(name="roles", required=false, defaultValue="") String roles){
        Set<Role> roleList = new HashSet<>();
        if(!roles.equals("")) {
            String[] array = roles.split(",");
            for (String id : array) {
                Role role = roleRepository.findOne(new Long(id));
                roleList.add(role);
            }
        }
        e.setRoleList(roleList);
        e = userRepository.save(e);
        return "redirect:/user/" + e.getId();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            userRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize);
        Page<User> page = userRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
