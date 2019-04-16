package com.hrada.oms.controller.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.model.BaseRepository;
import com.hrada.oms.dao.model.WellRepository;
import com.hrada.oms.model.model.Well;
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
@RequestMapping("/well")
public class WellController {

    @Autowired
    WellRepository wellRepository;

    @Autowired
    BaseRepository baseRepository;

    @RequestMapping("/findWellsByBase")
    @ResponseBody
    public String findWellsByBase(@RequestParam("id") long id){
        String rs = "";
        List<Well> list = wellRepository.findWellsByBase(baseRepository.getOne(id));
        for(Well well:list){
            rs += "<option value=\""+well.getId()+"\">"+well.getName()+"</option>";
        }
        return rs;
    }

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute(new Well());
        model.addAttribute("bases",baseRepository.findAll());
        return "/model/well/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        model.addAttribute("well",wellRepository.findAll());
        return "/model/well/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("well",wellRepository.findOne(id));
        model.addAttribute("bases",baseRepository.findAll());
        return "/model/well/detail";
    }

    @PostMapping("/save")
    public String save(Well well){
        wellRepository.save(well);
        return "redirect:/well/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public void delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            wellRepository.delete(o.getLong("id"));
        }
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize);
        Page<Well> page = wellRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }

}
