package com.hrada.oms.controller.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.model.BaseRepository;
import com.hrada.oms.dao.model.EquipmentRepository;
import com.hrada.oms.dao.model.WellRepository;
import com.hrada.oms.model.model.Equipment;
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
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    BaseRepository baseRepository;

    @Autowired
    WellRepository wellRepository;

    @RequestMapping("/findAll")
    @ResponseBody
    public JSONArray findAll(){
        JSONArray array = new JSONArray();
        List<Equipment> list = equipmentRepository.findAll();
        for(Equipment equipment:list){
            JSONObject obj = new JSONObject();
            obj.put("id", equipment.getId());
            obj.put("name", equipment.getName());
            array.add(obj);
        }
        return array;
    }

    @RequestMapping("/findByBase")
    @ResponseBody
    public String findEquipmentsByBase(@RequestParam("id") long id){
        String rs = "";
        List<Equipment> list = equipmentRepository.findByBase(baseRepository.getOne(id));
        for(Equipment equipment:list){
            rs += "<option value=\""+equipment.getId()+"\">"+equipment.getName()+"</option>";
        }
        return rs;
    }

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute(new Equipment());
        model.addAttribute("bases", baseRepository.findAll());
        model.addAttribute("wells", wellRepository.findAll());
        return "/model/equipment/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        model.addAttribute("equipment",equipmentRepository.findAll());
        return "/model/equipment/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("equipment",equipmentRepository.findOne(id));
        model.addAttribute("bases",baseRepository.findAll());
        model.addAttribute("wells", wellRepository.findAll());
        return "/model/equipment/detail";
    }

    @PostMapping("/save")
    public String save(Equipment equipment){
        equipmentRepository.save(equipment);
        return "redirect:/equipment/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public void delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            equipmentRepository.delete(o.getLong("id"));
        }
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize);
        Page<Equipment> page = equipmentRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }

}
