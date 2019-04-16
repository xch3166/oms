package com.hrada.oms.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.common.LogRepository;
import com.hrada.oms.model.common.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by shin on 2019-03-11.
 */
@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    LogRepository logRepository;

    @RequestMapping("/list")
    public String list(){
        return "/common/log/list";
    }

    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        Pageable pageable = new PageRequest(pageNumber-1, pageSize);
        Page<Log> page = logRepository.findAll(pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
