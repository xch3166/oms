package com.hrada.oms.controller.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.common.EnumRepository;
import com.hrada.oms.dao.log.TravelRepository;
import com.hrada.oms.dao.model.PersonalRepository;
import com.hrada.oms.model.common.User;
import com.hrada.oms.model.log.Travel;
import com.hrada.oms.service.TravelService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shin on 2018/10/24.
 */
@Controller
@RequestMapping("/travel")
public class TravelController {

    @Autowired
    TravelRepository travelRepository;

    @Autowired
    TravelService travelService;

    @Autowired
    PersonalRepository personalRepository;

    @Autowired
    EnumRepository enumRepository;

    @RequestMapping("/pass")
    public String pass(@RequestParam(value = "id") Long id){
        travelService.pass(id);
        return "redirect:/travel/list";
    }

    @RequestMapping("/refuse")
    public String refuse(@RequestParam(value = "id") Long id){
        travelService.refuse(id);
        return "redirect:/travel/list";
    }

    @RequestMapping("/add")
    public String add(Model model){
        Travel travel = new Travel();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        travel.setApplicant(user.getPersonal());
        model.addAttribute("travel", travel);
        model.addAttribute("update", true);
        model.addAttribute("approval", false);
        model.addAttribute("personals", personalRepository.findAll());
        model.addAttribute("types", enumRepository.findByParent_Name("出行方式"));
        return "/log/travel/detail";
    }

    @RequestMapping("/list")
    public String list(){
        return "/log/travel/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Travel travel = travelRepository.findOne(id);
        model.addAttribute("update", travel.getApplicant().getId() == user.getPersonal().getId());
        model.addAttribute("approval", user.getName().equals("李强") || user.getName().equals("王景丽"));
        model.addAttribute("travel", travel);
        model.addAttribute("personals", personalRepository.findAll());
        model.addAttribute("types", enumRepository.findByParent_Name("出行方式"));
        return "/log/travel/detail";
    }

    @PostMapping("/save")
    public String save(Travel travel){
        travelService.save(travel);
        return "redirect:/travel/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            travelRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        return travelService.data(pageNumber, pageSize);
    }
}
