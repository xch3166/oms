package com.hrada.oms.service.impl;

import com.hrada.oms.dao.log.OperationRepository;
import com.hrada.oms.dao.log.PartsRecordRepository;
import com.hrada.oms.dao.model.PartsRepository;
import com.hrada.oms.model.log.Operation;
import com.hrada.oms.model.log.OperationDetail;
import com.hrada.oms.model.log.PartsRecord;
import com.hrada.oms.model.model.Parts;
import com.hrada.oms.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shin on 2018/12/20.
 */
@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    OperationRepository operationRepository;

    @Autowired
    PartsRepository partsRepository;

    @Autowired
    PartsRecordRepository partsRecordRepository;

    @Override
    public Operation save(Operation operation) {
        if(operation.getId()==null){
            List<OperationDetail> list = operation.getDetails();
            for(OperationDetail detail:list){
                List<Parts> partsList = detail.getModule().getPartsList();
                for(Parts parts:partsList){
                    PartsRecord partsRecord = partsRecordRepository.findTopByPartsOrderByIdDesc(parts);
                    if(partsRecord!=null && partsRecord.getEDate()==null){
                        partsRecord.setHours(partsRecord.getHours()+detail.getHour());
                    }else{
                        partsRecord = new PartsRecord();
                        partsRecord.setSDate(operation.getLogDate());
                        partsRecord.setParts(parts);
                        partsRecord.setEquipment(detail.getEquipment());
                        partsRecord.setModule(detail.getModule());
                        partsRecord.setHours(detail.getHour());
                    }
                    partsRecordRepository.save(partsRecord);
                }
            }
        }
        return operationRepository.save(operation);
    }
}
