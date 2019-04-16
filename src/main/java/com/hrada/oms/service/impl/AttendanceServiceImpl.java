package com.hrada.oms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.log.AttendanceRepository;
import com.hrada.oms.model.common.User;
import com.hrada.oms.model.log.AttendanceLog;
import com.hrada.oms.service.AttendanceService;
import com.hrada.oms.util.DateUtil;
import org.apache.shiro.SecurityUtils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shin on 2019/1/17.
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    DateUtil dateUtil;

   @Override
    public JSONObject baseData(Integer pageNumber, Integer pageSize, final Long base) {
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        Specification<AttendanceLog> specification = new Specification<AttendanceLog>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("base"), base));
                predicates.add(criteriaBuilder.greaterThan(root.get("eDate"), new Date()));
                predicates.add(criteriaBuilder.lessThan(root.get("sDate"), new Date()));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<AttendanceLog> page = attendanceRepository.findAll(specification, pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }

    @Override
    public JSONObject data(Integer pageNumber, Integer pageSize, final Long personal, final Date sDate, final Date eDate) {
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        Page<AttendanceLog> page;
        if(SecurityUtils.getSubject().hasRole("super")){
            Specification<AttendanceLog> specification = new Specification<AttendanceLog>() {
                @Override
                public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    if(personal!=null){
                        predicates.add(criteriaBuilder.equal(root.get("personal"), personal));
                    }
                    if(sDate!=null){
                        predicates.add(criteriaBuilder.greaterThan(root.get("eDate"), sDate));
                    }
                    if(eDate!=null){
                        predicates.add(criteriaBuilder.lessThan(root.get("sDate"), eDate));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };
            page = attendanceRepository.findAll(specification, pageable);
        }else{
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            page = attendanceRepository.findAllByPersonal(pageable, user.getPersonal());
        }
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
