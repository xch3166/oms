package com.hrada.oms.controller.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.SafetyTrainingRepository;
import com.hrada.oms.dao.model.BaseRepository;
import com.hrada.oms.dao.model.PersonalRepository;
import com.hrada.oms.model.log.SafetyTraining;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shin on 2018/8/2.
 */
@Controller
@RequestMapping("/safetyTraining")
public class SafetyTrainingController {

    @Autowired
    SafetyTrainingRepository safetyTrainingRepository;

    @Autowired
    BaseRepository baseRepository;

    @Autowired
    PersonalRepository personalRepository;

    @RequestMapping("/list")
    public String list(){
        return "/log/safetyTraining/list";
    }

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute("safetyTraining",new SafetyTraining());
        model.addAttribute("bases",baseRepository.findAll());
        model.addAttribute("personals",personalRepository.findAll());
        return "/log/safetyTraining/detail";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("safetyTraining",safetyTrainingRepository.findOne(id));
        model.addAttribute("bases",baseRepository.findAll());
        model.addAttribute("personals",personalRepository.findAll());
        return "/log/safetyTraining/detail";
    }

    @PostMapping("/save")
    public String save(SafetyTraining safetyTraining){
        safetyTrainingRepository.save(safetyTraining);
        return "redirect:/safetyTraining/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            safetyTrainingRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        Page<SafetyTraining> page = safetyTrainingRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
