package com.hrada.oms.controller.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.SealRepository;
import com.hrada.oms.model.common.User;
import com.hrada.oms.model.log.Seal;
import com.hrada.oms.service.SealService;
import com.hrada.oms.util.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shin on 2018/10/19.
 */
@Controller
@RequestMapping("/seal")
public class SealController {

    @Autowired
    SealRepository sealRepository;

    @Autowired
    SealService sealService;

    @Autowired
    DateUtil dateUtil;

    @RequestMapping("/print")
    public String print(@RequestParam("array") String ids, Model model){
        List<Seal> list = new ArrayList<>();
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            list.add(sealRepository.findOne(o.getLong("id")));
        }
        model.addAttribute("list", list);
        return "/log/seal/print";
    }

    @RequestMapping("/pass")
    public String pass(@RequestParam(value = "id") Long id){
        sealService.pass(id);
        return "redirect:/seal/list";
    }

    @RequestMapping("/refuse")
    public String refuse(@RequestParam(value = "id") Long id){
        sealService.refuse(id);
        return "redirect:/seal/list";
    }

    @RequestMapping("/add")
    public String add(Model model){
        Seal seal = new Seal();
        seal.setLogDate(dateUtil.getCurrentDate("yyyy-MM-dd"));
        model.addAttribute("update", true);
        model.addAttribute("approval", false);
        model.addAttribute("seal", seal);
        return "/log/seal/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("personal", user.getPersonal());
        return "/log/seal/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Seal seal = sealRepository.findOne(id);
        model.addAttribute("update", seal.getApplicant().getId() == user.getPersonal().getId());
        model.addAttribute("approval", user.getName().equals("王景丽"));
        model.addAttribute("seal", seal);
        return "/log/seal/detail";
    }

    @PostMapping("/save")
    public String save(Seal seal){
        sealService.save(seal);
        return "redirect:/seal/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            sealRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                           @RequestParam(value = "sDate", required = false) Date sDate,
                           @RequestParam(value = "eDate", required = false) Date eDate){
        return sealService.data(pageNumber, pageSize, sDate, eDate);
    }
}
