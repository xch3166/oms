package com.hrada.oms.controller.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.common.PermissionRepository;
import com.hrada.oms.model.common.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shin on 2018/8/21.
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    PermissionRepository permissionRepository;

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute(new Permission());
        return "/common/permission/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        model.addAttribute("permission",permissionRepository.findAll());
        return "/common/permission/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("permission",permissionRepository.findOne(id));
        return "/common/permission/detail";
    }

    @PostMapping("/save")
    public String save(Permission e){
        e = permissionRepository.save(e);
        return "redirect:/permission/" + e.getId();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            permissionRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize);
        Page<Permission> page = permissionRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
