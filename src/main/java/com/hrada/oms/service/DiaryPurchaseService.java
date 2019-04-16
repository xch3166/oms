package com.hrada.oms.service;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.model.log.DiaryPurchase;

/**
 * Created by shin on 2018/11/27.
 */
public interface DiaryPurchaseService {

    DiaryPurchase save(DiaryPurchase diaryPurchase);
    void pass(Long id);
    void refuse(Long id);
    JSONObject data(Integer pageNumber, Integer pageSize);
}
