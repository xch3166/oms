package com.hrada.oms.service;

import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.model.log.Travel;

/**
 * Created by shin on 2018/10/24.
 */
public interface TravelService {

    Travel save(Travel travel);
    void pass(Long id);
    void refuse(Long id);
    JSONObject data(Integer pageNumber, Integer pageSize);
}
