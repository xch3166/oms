package com.hrada.oms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.model.SupplierRepository;
import com.hrada.oms.model.model.Supplier;
import com.hrada.oms.service.SupplierService;
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
import java.util.List;

/**
 * Created by shin on 2019-02-15.
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    @Override
    public JSONObject data(Integer pageNumber, Integer pageSize, String name) {
        Pageable pageable = new PageRequest(pageNumber-1, pageSize, Sort.Direction.DESC, "id");
        Specification specification = new Specification<Supplier>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(null != name){
                    predicates.add(criteriaBuilder.like(root.get("name"), "%"+name+"%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<Supplier> page = supplierRepository.findAll(specification, pageable);
        JSONObject obj = new JSONObject();
        obj.put("rows",page.getContent());
        obj.put("total",page.getTotalElements());
        return obj;
    }
}
