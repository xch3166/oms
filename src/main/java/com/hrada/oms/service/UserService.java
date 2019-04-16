package com.hrada.oms.service;

import com.hrada.oms.model.common.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by shin on 2017/9/13.
 */
public interface UserService{

    Page<User> findAll(Pageable pageable);
    User findByUname(String name);
}
