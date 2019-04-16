package com.hrada.oms.dao.log;

import com.hrada.oms.model.log.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2019/1/8.
 */
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
