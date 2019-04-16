package com.hrada.oms.model.common;

import com.hrada.oms.model.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by shin on 2018/11/2.
 */
@Data
@Entity
@Table(name = "common_log")
public class Log extends BaseEntity {

    @OneToOne
    private User user;
    private String ip;
    private String url;
    private String method;
    @Lob
    private String args;
    private String type;
}
