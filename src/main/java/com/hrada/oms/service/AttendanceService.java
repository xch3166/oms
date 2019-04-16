package com.hrada.oms.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * Created by shin on 2019/1/4.
 */
public interface AttendanceService {

    JSONObject data(Integer pageNumber, Integer pageSize, final Long personal, final Date sDate, final Date eDate);

    JSONObject baseData(Integer pageNumber, Integer pageSize, Long base);
}
