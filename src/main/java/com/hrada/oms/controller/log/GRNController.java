package com.hrada.oms.controller.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.ContractRepository;
import com.hrada.oms.dao.log.GRNRepository;
import com.hrada.oms.dao.log.InventoryRepository;
import com.hrada.oms.dao.model.*;
import com.hrada.oms.model.common.User;
import com.hrada.oms.model.log.GRN;
import com.hrada.oms.model.log.Inventory;
import com.hrada.oms.service.GRNService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shin on 2018/7/30.
 */
@Controller
@RequestMapping("/grn")
public class GRNController {

    @Autowired
    GRNRepository grnRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    PersonalRepository personalRepository;

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    GRNService grnService;

    @RequestMapping("/print/{id}")
    public String print(Model model, @PathVariable("id") long id){
        model.addAttribute("grn",grnRepository.findOne(id));
        model.addAttribute("suppliers",supplierRepository.findAll());
        model.addAttribute("personals",personalRepository.findAll());
        model.addAttribute("contracts",contractRepository.findAllByTypeOrderByCreateDateDesc(0));
        return "/log/grn/print";
    }

    @RequestMapping("/add")
    public String add(Model model){
        GRN grn = new GRN();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        grn.setNo(sdf.format(new Date()));
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        grn.setFounder(user.getPersonal());
        grn.setReviewer(user.getPersonal());
        model.addAttribute("grn",grn);
        model.addAttribute("suppliers",supplierRepository.findAll());
        model.addAttribute("personals",personalRepository.findAll());
        model.addAttribute("products",productRepository.findAll());
        model.addAttribute("contracts",contractRepository.findAllByTypeOrderByCreateDateDesc(0));
        return "/log/grn/detail";
    }

    @RequestMapping("/list")
    public String list(){
        return "/log/grn/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("grn",grnRepository.findOne(id));
        model.addAttribute("suppliers",supplierRepository.findAll());
        model.addAttribute("personals",personalRepository.findAll());
        model.addAttribute("products",productRepository.findAll());
        model.addAttribute("contracts",contractRepository.findAllByTypeOrderByCreateDateDesc(0));
        return "/log/grn/detail";
    }

    @PostMapping("/save")
    @RequiresRoles("采购")
    public String save(GRN grn){
        grnService.save(grn);
        return "redirect:/grn/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    @RequiresRoles("采购")
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            grnRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        Page<GRN> page = grnRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }

    @RequestMapping("/inventoryData")
    @ResponseBody
    public JSONObject inventoryData(@RequestParam(value = "inventory", defaultValue = "1") Long inventory,
                                    @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        List<Inventory> inventories = new ArrayList<>();
        inventories.add(inventoryRepository.findOne(inventory));
        Page<GRN> page = grnRepository.findAllByInventories(pageable, inventories);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
