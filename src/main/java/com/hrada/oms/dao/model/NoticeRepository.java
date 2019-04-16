package com.hrada.oms.dao.model;

import com.hrada.oms.model.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2018/10/16.
 */
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
