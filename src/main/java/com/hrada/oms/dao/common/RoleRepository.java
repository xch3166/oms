package com.hrada.oms.dao.common;

import com.hrada.oms.model.common.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2017/9/15.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String s);
}
