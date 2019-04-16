package com.hrada.oms.service.impl;

import com.hrada.oms.model.log.Safety;
import com.hrada.oms.dao.log.AccidentRepository;
import com.hrada.oms.dao.log.SafetyRepository;
import com.hrada.oms.model.log.Accident;
import com.hrada.oms.model.model.Base;
import com.hrada.oms.service.AccidentService;
import com.hrada.oms.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by shin on 2018/4/9.
 */
@Service
public class AccidentServiceImpl implements AccidentService {

    @Autowired
    AccidentRepository accidentRepository;

    @Autowired
    SafetyRepository safetyRepository;

    @Override
    public Accident save(Accident accident) {

        Base base = accident.getBase();
        Date sDate = base.getsDate();
        Date eDate = accident.getLogDate();

        Accident a = accidentRepository.findTopByBaseOrderByLogDateDesc(base);
        if(a!=null){
            sDate = a.getLogDate();
        }

        Safety safety = new Safety();
        safety.setBase(base);
        safety.setsDate(sDate);
        safety.seteDate(eDate);
        safety.setDays(DateUtil.diffDay(sDate, eDate));

        safetyRepository.save(safety);

        return accidentRepository.save(accident);
    }
}
