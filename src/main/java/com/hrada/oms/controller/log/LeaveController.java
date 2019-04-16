package com.hrada.oms.controller.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.common.EnumRepository;
import com.hrada.oms.dao.log.LeaveRepository;
import com.hrada.oms.dao.model.PersonalRepository;
import com.hrada.oms.model.common.User;
import com.hrada.oms.model.log.Leave;
import com.hrada.oms.service.LeaveService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shin on 2018/10/16.
 */
@Controller
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    LeaveRepository leaveRepository;

    @Autowired
    LeaveService leaveService;

    @Autowired
    EnumRepository enumRepository;

    @Autowired
    PersonalRepository personalRepository;

    @RequestMapping("/pass")
    public String pass(@RequestParam(value = "id") Long id){
        leaveService.pass(id);
        return "redirect:/leave/list";
    }

    @RequestMapping("/refuse")
    public String refuse(@RequestParam(value = "id") Long id, @RequestParam(value = "reply") String reply){
        leaveService.refuse(id, reply);
        return "redirect:/leave/list";
    }

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute("update", true);
        model.addAttribute("approval", false);
        model.addAttribute("leave", new Leave());
        model.addAttribute("types", enumRepository.findByParent_Name("请假类型"));
        return "/log/leave/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        model.addAttribute("personals", personalRepository.findAll());
        return "/log/leave/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Leave leave = leaveRepository.findOne(id);
        model.addAttribute("update", leave.getApplicant().getId() == user.getPersonal().getId());
        model.addAttribute("approval", subject.isPermitted("approval_leave"));
        model.addAttribute("leave", leave);
        model.addAttribute("types", enumRepository.findByParent_Name("请假类型"));
        return "/log/leave/detail";
    }

    @PostMapping("/save")
    public String save(Leave leave){
        leaveService.save(leave);
        return "redirect:/leave/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            leaveRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                           @RequestParam(value = "applicant", required = false) Long applicant,
                           @RequestParam(value = "month", required = false) Integer month){
        return leaveService.data(pageNumber, pageSize, applicant, month);
    }
}
