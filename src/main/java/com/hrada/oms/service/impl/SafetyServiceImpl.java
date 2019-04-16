package com.hrada.oms.service.impl;

import com.hrada.oms.dao.log.SafetyRepository;
import com.hrada.oms.dao.model.BaseRepository;
import com.hrada.oms.model.log.Safety;
import com.hrada.oms.service.SafetyService;
import com.hrada.oms.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by shin on 2018/8/14.
 */
@Service
public class SafetyServiceImpl implements SafetyService {

    @Autowired
    SafetyRepository safetyRepository;

    @Autowired
    BaseRepository baseRepository;

    @Autowired
    DateUtil dateUtil;

    @Override
    public Integer getDays() {
        Safety safety = safetyRepository.findTopByOrderByEDateDesc();
        Date sd = new Date();
        if(safety!=null){
            sd = safety.geteDate();
        }else{
            sd = baseRepository.getOne(new Long("4")).getsDate();
        }
        return dateUtil.diffDay(sd, new Date());
    }
}
