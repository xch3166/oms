package com.hrada.oms.dao.common;

import com.hrada.oms.model.common.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by shin on 2017/11/29.
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long >{

    @Override
    Page<Permission> findAll(Pageable pageable);
}
