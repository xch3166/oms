package com.hrada.oms.controller.log;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.InventoryRepository;
import com.hrada.oms.model.log.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by shin on 2019-03-13.
 */
@Controller
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    InventoryRepository inventoryRepository;

    @RequestMapping("/grn")
    public String grn(@RequestParam(value = "inventory") Long inventory, Model model){
        model.addAttribute("inventory", inventoryRepository.findOne(inventory));
        return "/log/inventory/grn";
    }

    @RequestMapping("/gdn")
    public String gdn(@RequestParam(value = "inventory") Long inventory, Model model){
        model.addAttribute("inventory", inventoryRepository.findOne(inventory));
        return "/log/inventory/gdn";
    }

    @RequestMapping("/list")
    public String list(){
        return "/log/inventory/list";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                           @RequestParam(value = "applicant", required = false) Long applicant){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        Page<Inventory> page = inventoryRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
