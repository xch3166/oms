package com.hrada.oms.controller.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.ContractRepository;
import com.hrada.oms.dao.log.GDNRepository;
import com.hrada.oms.dao.log.InventoryRepository;
import com.hrada.oms.dao.model.ClientRepository;
import com.hrada.oms.dao.model.PersonalRepository;
import com.hrada.oms.dao.model.ProductRepository;
import com.hrada.oms.model.common.User;
import com.hrada.oms.model.log.GDN;
import com.hrada.oms.model.log.Inventory;
import com.hrada.oms.service.GDNService;
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
@RequestMapping("/gdn")
public class GDNController {

    @Autowired
    GDNRepository gdnRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PersonalRepository personalRepository;

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    GDNService gdnService;

    @RequestMapping("/pass")
    public String pass(@RequestParam(value = "id") Long id){
        gdnService.pass(id);
        return "redirect:/gdn/list";
    }

    @RequestMapping("/refuse")
    public String refuse(@RequestParam(value = "id") Long id){
        gdnService.refuse(id);
        return "redirect:/gdn/list";
    }

    @RequestMapping("/reSub")
    public String reSub(@RequestParam(value = "id") Long id){
        gdnService.reSub(id);
        return "redirect:/gdn/list";
    }

    @RequestMapping("/print/{id}")
    public String print(Model model, @PathVariable("id") long id){
        model.addAttribute("gdn",gdnRepository.findOne(id));
        model.addAttribute("clients",clientRepository.findAll());
        model.addAttribute("personals",personalRepository.findAll());
        return "/log/gdn/print";
    }

    @RequestMapping("/add")
    public String add(Model model){
        GDN gdn = new GDN();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        gdn.setNo(sdf.format(new Date()));
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        gdn.setApplicant(user.getPersonal());
        model.addAttribute("gdn", gdn);
        model.addAttribute("clients",clientRepository.findAll());
        model.addAttribute("personals",personalRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("contracts",contractRepository.findAllByTypeOrderByCreateDateDesc(1));
        return "/log/gdn/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("personal", user.getPersonal());
        return "/log/gdn/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("gdn",gdnRepository.findOne(id));
        model.addAttribute("clients",clientRepository.findAll());
        model.addAttribute("personals",personalRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("contracts",contractRepository.findAllByTypeOrderByCreateDateDesc(1));
        return "/log/gdn/detail";
    }

    @PostMapping("/save")
    @RequiresRoles("采购")
    public String save(GDN gdn){
        gdnService.save(gdn);
        return "redirect:/gdn/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    @RequiresRoles("采购")
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            gdnRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        Page<GDN> page = gdnRepository.findAll(pageable);
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
        Page<GDN> page = gdnRepository.findAllByInventories(pageable, inventories);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
