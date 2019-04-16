package com.hrada.oms.controller.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.model.NoticeRepository;
import com.hrada.oms.model.model.Notice;
import com.hrada.oms.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shin on 2018/10/16.
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    NoticeService noticeService;

    @RequestMapping("/sendMessage")
    @ResponseBody
    public String sendMessage(@RequestParam("id") Long id){
        noticeService.sendMessage(id);
        return "ok";
    }

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute(new Notice());
        return "/model/notice/detail";
    }

    @RequestMapping("/list")
    public String list(){
        return "/model/notice/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("notice",noticeRepository.findOne(id));
        return "/model/notice/detail";
    }

    @PostMapping("/save")
    public String save(Notice notice){
        noticeRepository.save(notice);
        return "redirect:/notice/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public void delete(@RequestParam("array") String ids){
        JSONArray array = JSONArray.parseArray(ids);
        for(Object obj : array){
            JSONObject o = (JSONObject) obj;
            noticeRepository.delete(o.getLong("id"));
        }
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        Page<Notice> page = noticeRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
