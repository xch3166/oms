package com.hrada.oms.controller.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.model.BaseRepository;
import com.hrada.oms.dao.model.PersonalRepository;
import com.hrada.oms.dao.model.SupplierRepository;
import com.hrada.oms.model.model.Supplier;
import com.hrada.oms.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shin on 2018/7/30.
 */
@Controller
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    SupplierService supplierService;

    @Autowired
    BaseRepository baseRepository;

    @Autowired
    PersonalRepository personalRepository;


    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute("supplier",new Supplier());
        model.addAttribute("personals",personalRepository.findAll());
        return "/model/supplier/detail";
    }

    @RequestMapping("/list")
    public String list(){
        return "/model/supplier/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("supplier",supplierRepository.findOne(id));
        model.addAttribute("personals",personalRepository.findAll());
        return "/model/supplier/detail";
    }

    @PostMapping("/save")
    public String save(Supplier supplier){
        supplierRepository.save(supplier);
        return "redirect:/supplier/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            supplierRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                           @RequestParam(value = "name", required = false) String name){
        return supplierService.data(pageNumber, pageSize, name);
    }
}
