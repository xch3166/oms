package com.hrada.oms.service;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.model.log.Leave;

/**
 * Created by shin on 2018/10/17.
 */
public interface LeaveService {

    Leave save(Leave leave);
    void pass(Long id);
    void refuse(Long id, String reply);
    JSONObject data(Integer pageNumber, Integer pageSize, Long applicant, Integer month);
}
