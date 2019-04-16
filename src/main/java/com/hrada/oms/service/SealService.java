package com.hrada.oms.service;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.model.log.Seal;

import java.util.Date;

/**
 * Created by shin on 2018/10/19.
 */
public interface SealService {

    Seal save(Seal seal);
    void pass(Long id);
    void refuse(Long id);
    JSONObject data(Integer pageNumber, Integer pageSize, Date sDate, Date eDate);
}
