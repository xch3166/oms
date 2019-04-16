package com.hrada.oms.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by shin on 2019-02-15.
 */
public interface ClientService {

    JSONObject data(Integer pageNumber, Integer pageSize, String name);
}
