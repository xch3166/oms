package com.hrada.oms.dao.log;

import com.hrada.oms.model.log.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2018/10/30.
 */
public interface MailRepository extends JpaRepository<Mail, Long> {
}
