package com.hrada.oms.dao.model;

import com.hrada.oms.model.common.User;
import com.hrada.oms.model.model.Personal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2018/3/30.
 */
public interface PersonalRepository extends JpaRepository<Personal , Long> {

    Page<Personal> findAllByUser(Pageable pageable, User user);
}
