package com.hrada.oms.controller.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.DiaryPurchaseRepository;
import com.hrada.oms.model.log.DiaryPurchase;
import com.hrada.oms.service.DiaryPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shin on 2018/10/19.
 */
@Controller
@RequestMapping("/diaryPurchase")
public class DiaryPurchaseController {

    @Autowired
    DiaryPurchaseRepository diaryPurchaseRepository;

    @Autowired
    DiaryPurchaseService diaryPurchaseService;

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute(new DiaryPurchase());
        return "/log/diaryPurchase/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        return "/log/diaryPurchase/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("diaryPurchase",diaryPurchaseRepository.findOne(id));
        return "/log/diaryPurchase/detail";
    }

    @PostMapping("/save")
    public String save(DiaryPurchase purchase){
        diaryPurchaseService.save(purchase);
        return "redirect:/diaryPurchase/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            diaryPurchaseRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        return diaryPurchaseService.data(pageNumber, pageSize);
    }
}
