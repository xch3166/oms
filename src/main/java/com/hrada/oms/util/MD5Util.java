package com.hrada.oms.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Component;

/**
 * Created by shin on 2018/11/8.
 */
@Component
public class MD5Util {

    public String toMd5(String pwd,String salt,int i){
        Md5Hash toMd5 = new Md5Hash(pwd,salt,i);
        return toMd5.toString();
    }


}
