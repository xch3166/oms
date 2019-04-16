package com.hrada.oms.controller.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.model.BaseRepository;
import com.hrada.oms.dao.model.PersonalRepository;
import com.hrada.oms.model.model.Base;
import com.hrada.oms.service.AccidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shin on 2018/3/30.
 */
@Controller
@RequestMapping("/base")
public class BaseController {

    @Autowired
    BaseRepository baseRepository;

    @Autowired
    AccidentService accidentService;

    @Autowired
    PersonalRepository personalRepository;

    @RequestMapping("/{id}/personal")
    public String personal(Model model, @PathVariable("id") long id){
        model.addAttribute("base", id);
        return "/model/base/personal";
    }

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute(new Base());
        model.addAttribute("personals",personalRepository.findAll());
        return "/model/base/detail";
    }

    @RequestMapping("/list")
    public String list(){
        return "/model/base/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("base",baseRepository.findOne(id));
        model.addAttribute("personals",personalRepository.findAll());
        return "/model/base/detail";
    }

    @PostMapping("/save")
    public String save(Base base){
        baseRepository.save(base);
        return "redirect:/base/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            baseRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize);
        Page<Base> page = baseRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }

}
