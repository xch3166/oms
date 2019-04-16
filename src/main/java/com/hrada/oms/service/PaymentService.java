package com.hrada.oms.service;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.model.log.Payment;

import java.util.Date;

/**
 * Created by shin on 2018/10/17.
 */
public interface PaymentService {

    Payment save(Payment payment);
    void pass(Long id);
    void refuse(Long id);
    void reSub(Long id);
    JSONObject data(Integer pageNumber, Integer pageSize, Date logDate, String receipt, Long applicant);

    JSONObject getPieData(Integer year);
    JSONObject getLineData(Integer year);
}
