package com.hrada.oms.service.impl;

import com.hrada.oms.dao.common.UserRepository;
import com.hrada.oms.model.common.User;
import com.hrada.oms.service.UserService;
import com.hrada.oms.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by shin on 2017/9/13.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MD5Util md5Util;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findByUname(String name){
        return userRepository.findByUname(name);
    }

}
