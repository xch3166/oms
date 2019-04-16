package com.hrada.oms.controller.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.AttendanceRepository;
import com.hrada.oms.dao.model.BaseRepository;
import com.hrada.oms.dao.model.PersonalRepository;
import com.hrada.oms.model.common.User;
import com.hrada.oms.model.log.AttendanceLog;
import com.hrada.oms.service.AttendanceService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by shin on 2018/3/30.
 */
@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    BaseRepository baseRepository;

    @Autowired
    PersonalRepository personalRepository;

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute("update", true);
        model.addAttribute("attendance",new AttendanceLog());
        model.addAttribute("bases",baseRepository.findAll());
        model.addAttribute("personals",personalRepository.findAll());
        return "/log/attendance/detail";
    }

    @RequestMapping("/list")
    public String list(Model model){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("personal", user.getPersonal());
        model.addAttribute("personals",personalRepository.findAll());
        return "/log/attendance/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("update", SecurityUtils.getSubject().hasRole("super") || user.getUname().equals("xiejg@hrada.net") || user.getUname().equals("yaolj@hrada.net"));
        model.addAttribute("attendance",attendanceRepository.findOne(id));
        model.addAttribute("bases",baseRepository.findAll());
        model.addAttribute("personals",personalRepository.findAll());
        return "/log/attendance/detail";
    }

    @PostMapping("/save")
    public String save(AttendanceLog attendanceLog){
        attendanceRepository.save(attendanceLog);
        return "redirect:/attendance/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            attendanceRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                           @RequestParam(value = "personal", required = false) Long personal,
                           @RequestParam(value = "sDate", required = false) Date sDate,
                           @RequestParam(value = "eDate", required = false) Date eDate){
        return attendanceService.data(pageNumber, pageSize, personal, sDate, eDate);
    }

    @RequestMapping("/base/data")
    @ResponseBody
    public JSONObject baseData(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                           @RequestParam(value = "base", required = false) Long base){
        return attendanceService.baseData(pageNumber, pageSize, base);
    }

}
