package com.hrada.oms.dao.common;

import com.hrada.oms.model.common.Role;
import com.hrada.oms.model.common.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by shin on 2017/9/13.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUname(String name);
    List<User> findAllByRoleList(Role role);
    List<User> findAll(Specification<User> specification);
}
