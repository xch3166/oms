package com.hrada.oms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.InjectionRepository;
import com.hrada.oms.model.log.InjectionLog;
import com.hrada.oms.service.InjectionService;
import com.hrada.oms.util.DateUtil;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shin on 2019/1/15.
 */
@Service
public class InjectionServiceImpl implements InjectionService {

    @Autowired
    InjectionRepository injectionRepository;

    @Autowired
    DateUtil dateUtil;

    @Override
    public void export(final Date sDate, final Date eDate, HttpServletResponse response) {
        List<InjectionLog> list = injectionRepository.findAll(spec(sDate, eDate));

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("回注记录");

        String[] header = {"序号", "日期", "基地", "接收固项总量", "接收液项总量", "接收总量", "回注总量", "回注时间", "洗车次数"};
        HSSFRow headRow = sheet.createRow(0);
        for (int i=0; i<header.length; i++) {
            headRow.createCell(i).setCellValue(new HSSFRichTextString(header[i]));
        }

        Integer solid=0, liquid=0, receive=0, injection=0, count=0; Double hours = 0.0;
        for(int i=0; i<list.size(); i++){
            HSSFRow row = sheet.createRow(i+1);
            InjectionLog injectionLog = list.get(i);
            row.createCell(0).setCellValue(i+1);
            row.createCell(1).setCellValue(new HSSFRichTextString(injectionLog.getLogDate().toString()));
            row.createCell(2).setCellValue(injectionLog.getBase().getName());
            row.createCell(3).setCellValue(injectionLog.getSolid());
            row.createCell(4).setCellValue(injectionLog.getLiquid());
            row.createCell(5).setCellValue(injectionLog.getReceive());
            row.createCell(6).setCellValue(injectionLog.getInjection());
            row.createCell(7).setCellValue(injectionLog.getHours());
            row.createCell(8).setCellValue(injectionLog.getN30());

            solid += injectionLog.getSolid();
            liquid += injectionLog.getLiquid();
            receive += injectionLog.getReceive();
            injection += injectionLog.getInjection();
            hours += injectionLog.getHours();
            count += injectionLog.getN30();
        }

        HSSFRow footRow = sheet.createRow(list.size()+1);
        footRow.createCell(0).setCellValue("合计");
        footRow.createCell(1).setCellValue("");
        footRow.createCell(2).setCellValue("");
        footRow.createCell(3).setCellValue(solid);
        footRow.createCell(4).setCellValue(liquid);
        footRow.createCell(5).setCellValue(receive);
        footRow.createCell(6).setCellValue(injection);
        footRow.createCell(7).setCellValue(hours);
        footRow.createCell(8).setCellValue(count);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename="+dateUtil.convert2String(sDate, "yyyy-MM-dd")+" - "+dateUtil.convert2String(eDate, "yyyy-MM-dd")+".xls");
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject data(Integer pageNumber, Integer pageSize, final Date sDate, final Date eDate) {
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, new Sort(Sort.Direction.DESC, "id"));
        Page<InjectionLog> page = injectionRepository.findAll(spec(sDate, eDate), pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }

    static Specification spec(final Date sDate, final Date eDate) {
        Specification<InjectionLog> specification = new Specification<InjectionLog>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(sDate!=null){
                    predicates.add(criteriaBuilder.greaterThan(root.get("logDate"), sDate));
                }
                if(eDate!=null){
                    predicates.add(criteriaBuilder.lessThan(root.get("logDate"), eDate));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return specification;
    }
}
