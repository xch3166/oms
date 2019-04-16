package com.hrada.oms.controller.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.model.BaseRepository;
import com.hrada.oms.dao.model.EquipmentRepository;
import com.hrada.oms.dao.model.ModuleRepository;
import com.hrada.oms.model.model.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by shin on 2018/3/30.
 */
@Controller
@RequestMapping("/module")
public class ModuleController {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    BaseRepository baseRepository;

    @Autowired
    EquipmentRepository equipmentRepository;

    @RequestMapping("/findAll")
    @ResponseBody
    public JSONArray findAll(){
        JSONArray array = new JSONArray();
        List<Module> list = moduleRepository.findAll();
        toJsonArray(array, list);
        return array;
    }

    private void toJsonArray(JSONArray array, List<Module> list) {
        for(Module module:list){
            JSONObject obj = new JSONObject();
            obj.put("id", module.getId());
            obj.put("name", module.getName());
            array.add(obj);
        }
    }

    @RequestMapping("/findByEquipment")
    @ResponseBody
    public JSONArray findByEquipment(@RequestParam("id") Long id){
        JSONArray array = new JSONArray();
        List<Module> list = moduleRepository.findByEquipment(equipmentRepository.findOne(id));
        toJsonArray(array, list);
        return array;
    }

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute(new Module());
        model.addAttribute("bases", baseRepository.findAll());
        model.addAttribute("equipments", equipmentRepository.findAll());
        return "/model/module/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        model.addAttribute("module",moduleRepository.findAll());
        return "/model/module/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("module",moduleRepository.findOne(id));
        model.addAttribute("bases",baseRepository.findAll());
        model.addAttribute("equipments", equipmentRepository.findAll());
        return "/model/module/detail";
    }

    @PostMapping("/save")
    public String save(Module module){
        moduleRepository.save(module);
        return "redirect:/module/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public void delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            moduleRepository.delete(o.getLong("id"));
        }
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize);
        Page<Module> page = moduleRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }

}
