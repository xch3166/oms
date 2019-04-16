package com.hrada.oms.model.common;

import com.hrada.oms.model.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by shin on 2018/11/16.
 */
@Data
@Entity
@Table(name = "common_upload")
public class Upload extends BaseEntity {

    private String url;
    private String oldName;
}
