package com.hrada.oms.service.impl;

import com.hrada.oms.dao.log.PartsRecordRepository;
import com.hrada.oms.dao.log.ServiceRepository;
import com.hrada.oms.dao.model.PartsRepository;
import com.hrada.oms.model.log.PartsRecord;
import com.hrada.oms.model.log.ServiceLog;
import com.hrada.oms.model.log.ServiceParts;
import com.hrada.oms.model.model.Parts;
import com.hrada.oms.service.ServiceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shin on 2018/8/2.
 */
@Service
public class ServiceLogServiceImpl implements ServiceLogService {

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    PartsRepository partsRepository;

    @Autowired
    PartsRecordRepository partsRecordRepository;

    @Override
    public ServiceLog save(ServiceLog serviceLog) {
        serviceLog = serviceRepository.save(serviceLog);
        List<ServiceParts> list = serviceLog.getServiceParts();
        for(ServiceParts sp:list){
            Parts parts = sp.getParts();
            parts.setAmount(parts.getAmount()-sp.getAmount());
            partsRepository.save(parts);

            PartsRecord partsRecord = partsRecordRepository.findTopByPartsOrderByIdDesc(parts);
            if(partsRecord!=null){
                partsRecord.setEDate(serviceLog.getLogDate());
                partsRecordRepository.save(partsRecord);
            }
        }
        return serviceLog;
    }
}
