package com.hrada.oms.controller.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.common.EnumRepository;
import com.hrada.oms.dao.common.UserRepository;
import com.hrada.oms.dao.model.PersonalRepository;
import com.hrada.oms.model.common.User;
import com.hrada.oms.model.model.Personal;
import com.hrada.oms.service.PersonalService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shin on 2018/3/30.
 */
@Controller
@RequestMapping("/personal")
public class PersonalController {

    @Autowired
    PersonalRepository personalRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EnumRepository enumRepository;

    @Autowired
    PersonalService personalService;

    @RequestMapping("/birthday")
    @ResponseBody
    public JSON birthday(){
        return personalService.birthday();
    }

    @RequestMapping("/update")
    public String update(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Personal personal = user.getPersonal();
        return "redirect:/personal/" + personal.getId();
    }

    @RequestMapping("/add")
    public String add(Model model){
        Personal personal = new Personal();
        model.addAttribute(personal);
        model.addAttribute("companies", enumRepository.findByParent_Name("所属公司"));
        return "/model/personal/detail";
    }

    @RequestMapping("/list")
    public String list(){
        return "/model/personal/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("personal", personalRepository.findOne(id));
        model.addAttribute("companies", enumRepository.findByParent_Name("所属公司"));
        return "/model/personal/detail";
    }

    @PostMapping("/save")
    public String save(Personal personal){
        personalService.save(personal);
        return "redirect:/personal/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public void delete(@RequestParam long id){
        personalRepository.delete(id);
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        return personalService.data(pageNumber, pageSize);
    }

}
