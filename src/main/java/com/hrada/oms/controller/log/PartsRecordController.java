package com.hrada.oms.controller.log;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.PartsRecordRepository;
import com.hrada.oms.model.log.PartsRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by shin on 2018/12/24.
 */
@Controller
@RequestMapping("/partsRecord")
public class PartsRecordController {

    @Autowired
    PartsRecordRepository partsRecordRepository;

    @RequestMapping("/list/{id}")
    public String list(Model model, @PathVariable("id") long id){
        model.addAttribute("id", id);
        return "/log/partsRecord/list";
    }

    @RequestMapping("/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        PartsRecord partsRecord = partsRecordRepository.findOne(id);
        model.addAttribute("partsRecord", partsRecord);
        return "/log/partsRecord/detail";
    }

    @RequestMapping("/data/{id}")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                           @PathVariable("id") long id){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC,"id");
        Page<PartsRecord> page = partsRecordRepository.findAlllByParts_Id(pageable, id);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
