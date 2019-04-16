package com.hrada.oms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.InjectionRepository;
import com.hrada.oms.model.log.InjectionLog;
import com.hrada.oms.service.ChartService;
import com.hrada.oms.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by shin on 2018/8/15.
 */
@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    InjectionRepository injectionRepository;

    @Autowired
    DateUtil dateUtil;

    @Override
    public JSON getInjectionLine() {
        List<InjectionLog> list = injectionRepository.findTop15ByOrderByLogDateDesc();
        Collections.reverse(list);
        JSONObject object = new JSONObject();
        String[] category = new String[15];
        Integer[] receive = new Integer[15];
        Integer[] injection = new Integer[15];
        for(int i=0;i<list.size();i++){
            InjectionLog injectionLog = list.get(i);
            category[i] = dateUtil.convert2String(injectionLog.getLogDate(),"MM-dd");
            receive[i] = injectionLog.getReceive()==null?0:injectionLog.getReceive();
            injection[i] = injectionLog.getInjection()==null?0:injectionLog.getInjection();
        }
        object.put("category", category);
        object.put("receive", receive);
        object.put("injection", injection);
        return object;
    }
}
