package com.hrada.oms.service;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.model.log.Contract;

/**
 * Created by shin on 2019-02-15.
 */
public interface ContractService {

    Contract save(Contract contract);
    JSONObject sellData(Integer pageNumber, Integer pageSize, final Long seller, final Long client);
    JSONObject purchaseData(Integer pageNumber, Integer pageSize, final Long supplier);
    void pass(Long id);
    void refuse(Long id);
    void reSub(Long id);
}
