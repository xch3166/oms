package com.hrada.oms.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.model.model.Personal;

import java.util.List;

/**
 * Created by shin on 2018/5/10.
 */
public interface PersonalService {

    Personal save(Personal personal);
    JSONObject data(Integer pageNumber, Integer pageSize);
    JSONArray birthday();
    List<Personal> findSeller();
}
