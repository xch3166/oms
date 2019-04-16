package com.hrada.oms.model.log;

import com.hrada.oms.model.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by shin on 2018/10/30.
 */
@Data
@Entity
@Table(name = "log_mail")
public class Mail extends BaseEntity {

    private String receiver;
    private String cc;
    private String subject;
    private String content;
    private String filePath;
    private String template;
    private Boolean isHtml = true;
}
