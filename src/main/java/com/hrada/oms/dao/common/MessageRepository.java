package com.hrada.oms.dao.common;

import com.hrada.oms.model.common.Message;
import com.hrada.oms.model.common.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by shin on 2018/11/7.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findAllByUser(Pageable pageable, User user);
    List<Message> findAllByUserAndStateOrderByIdDesc(User user, Integer state);
}
