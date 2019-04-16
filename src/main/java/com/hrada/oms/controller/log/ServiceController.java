package com.hrada.oms.controller.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.ServiceRepository;
import com.hrada.oms.dao.model.EquipmentRepository;
import com.hrada.oms.dao.model.ModuleRepository;
import com.hrada.oms.dao.model.PartsRepository;
import com.hrada.oms.model.log.ServiceLog;
import com.hrada.oms.service.ServiceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shin on 2018/3/30.
 */
@Controller
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    ServiceLogService serviceLogService;

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    PartsRepository partsRepository;

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute("service",new ServiceLog());
        model.addAttribute("equipments",equipmentRepository.findAll());
        model.addAttribute("modules", moduleRepository.findAll());
        model.addAttribute("parts",partsRepository.findAll());
        return "/log/service/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        model.addAttribute("service",serviceRepository.findAll());
        return "/log/service/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("service",serviceRepository.findOne(id));
        model.addAttribute("equipments",equipmentRepository.findAll());
        model.addAttribute("modules", moduleRepository.findAll());
        model.addAttribute("parts",partsRepository.findAll());
        return "/log/service/detail";
    }

    @PostMapping("/save")
    public String save(ServiceLog serviceLog){
        serviceLogService.save(serviceLog);
        return "redirect:/service/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            serviceRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        Page<ServiceLog> page = serviceRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }

}
