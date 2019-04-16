package com.hrada.oms.controller.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.SafetyRepository;
import com.hrada.oms.dao.model.BaseRepository;
import com.hrada.oms.model.log.Safety;
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
@RequestMapping("/safety")
public class SafetyController {

    @Autowired
    SafetyRepository safetyRepository;

    @Autowired
    BaseRepository baseRepository;

    @RequestMapping("/list")
    public String list(){
        return "/log/safety/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("safety",safetyRepository.findOne(id));
        model.addAttribute("bases",baseRepository.findAll());
        return "/log/safety/detail";
    }

    @PostMapping("/save")
    public String save(Safety safety){
        safetyRepository.save(safety);
        return "redirect:/safety/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            safetyRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        Page<Safety> page = safetyRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }

}
