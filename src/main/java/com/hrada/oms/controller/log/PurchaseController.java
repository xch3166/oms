package com.hrada.oms.controller.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.PurchaseRepository;
import com.hrada.oms.dao.model.SupplierRepository;
import com.hrada.oms.model.log.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shin on 2018/9/20.
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    SupplierRepository supplierRepository;


    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute(new Purchase());
        model.addAttribute("suppliers",supplierRepository.findAll());
        return "/log/purchase/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        model.addAttribute("purchase",purchaseRepository.findAll());
        return "/log/purchase/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("purchase",purchaseRepository.findOne(id));
        model.addAttribute("suppliers",supplierRepository.findAll());
        return "/log/purchase/detail";
    }

    @PostMapping("/save")
    public String save(Purchase purchase){
        purchaseRepository.save(purchase);
        return "redirect:/purchase/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            purchaseRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        Page<Purchase> page = purchaseRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
