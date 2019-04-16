package com.hrada.oms.service;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by shin on 2019/1/15.
 */
public interface InjectionService {

    void export(Date sDate, Date eDate, HttpServletResponse response);
    JSONObject data(Integer pageNumber, Integer pageSize, Date sDate, Date eDate);
}
