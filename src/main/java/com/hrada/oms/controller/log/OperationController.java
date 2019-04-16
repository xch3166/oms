package com.hrada.oms.controller.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.OperationRepository;
import com.hrada.oms.dao.model.BaseRepository;
import com.hrada.oms.dao.model.EquipmentRepository;
import com.hrada.oms.dao.model.ModuleRepository;
import com.hrada.oms.model.log.Operation;
import com.hrada.oms.service.OperationService;
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
@RequestMapping("/operation")
public class OperationController {

    @Autowired
    OperationRepository operationRepository;

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    BaseRepository baseRepository;

    @Autowired
    OperationService operationService;

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute(new Operation());
        model.addAttribute("bases",baseRepository.findAll());
        model.addAttribute("equipments",equipmentRepository.findAll());
        model.addAttribute("modules", moduleRepository.findAll());
        return "/log/operation/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        model.addAttribute("operation",operationRepository.findAll());
        return "/log/operation/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("operation",operationRepository.findOne(id));
        model.addAttribute("bases",baseRepository.findAll());
        model.addAttribute("equipments",equipmentRepository.findAll());
        model.addAttribute("modules", moduleRepository.findAll());
        return "/log/operation/detail";
    }

    @PostMapping("/save")
    public String save(Operation operation){
        operationService.save(operation);
        return "redirect:/operation/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            operationRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        Page<Operation> page = operationRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }

}
