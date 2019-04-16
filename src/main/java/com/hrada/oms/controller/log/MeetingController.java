package com.hrada.oms.controller.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.MeetingRepository;
import com.hrada.oms.model.log.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shin on 2019/1/8.
 */
@Controller
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    MeetingRepository meetingRepository;

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute("meeting",new Meeting());
        return "/log/meeting/detail";
    }

    @RequestMapping("/list")
    public String list(){
        return "/log/meeting/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("meeting", meetingRepository.findOne(id));
        return "/log/meeting/detail";
    }

    @PostMapping("/save")
    public String save(Meeting meeting){
        meetingRepository.save(meeting);
        return "redirect:/meeting/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            meetingRepository.delete(o.getLong("id"));
        }
        return "ok";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        Page<Meeting> page = meetingRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }

}
