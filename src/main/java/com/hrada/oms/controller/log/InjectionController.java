package com.hrada.oms.controller.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.InjectionRepository;
import com.hrada.oms.dao.model.BaseRepository;
import com.hrada.oms.model.log.InjectionLog;
import com.hrada.oms.service.InjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by shin on 2018/3/30.
 */
@Controller
@RequestMapping("/injection")
public class InjectionController {

    @Autowired
    InjectionRepository injectionRepository;

    @Autowired
    BaseRepository baseRepository;

    @Autowired
    InjectionService injectionService;

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute("injection",new InjectionLog());
        model.addAttribute("bases",baseRepository.findAll());
        return "/log/injection/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        model.addAttribute("injection",injectionRepository.findAll());
        return "/log/injection/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("injection",injectionRepository.findOne(id));
        model.addAttribute("bases",baseRepository.findAll());
        return "/log/injection/detail";
    }

    @PostMapping("/save")
    public String save(InjectionLog injectionLog){
        injectionRepository.save(injectionLog);
        return "redirect:/injection/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            injectionRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                           @RequestParam(value = "sDate", required = false) final Date sDate,
                           @RequestParam(value = "eDate", required = false) final Date eDate){
        return injectionService.data(pageNumber, pageSize, sDate, eDate);
    }

    @RequestMapping("/export")
    public void export(@RequestParam(value = "sDate", required = false) Date sDate,
                       @RequestParam(value = "eDate", required = false) Date eDate,
                       HttpServletResponse response){
        injectionService.export(sDate, eDate, response);
    }

}
