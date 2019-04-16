package com.hrada.oms.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by shin on 2019/1/7.
 */
@Component
public class LunarUtil {

    static SimpleDateFormat chineseDateFormat = new SimpleDateFormat(
            "yyyy年MM月dd日");

    public String getLunar(String date){
        Calendar today = Calendar.getInstance();
        String rs = "";
        try {
            today.setTime(chineseDateFormat.parse(date));
            Lunar lunar = new Lunar(today);
            rs = lunar.toString();
        } catch (ParseException e) {
            e.printStackTrace( );
        }
        return rs;
    }
}
