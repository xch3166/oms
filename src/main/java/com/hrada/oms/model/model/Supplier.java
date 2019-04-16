package com.hrada.oms.model.model;

import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.common.Upload;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 供应商
 * Created by shin on 2018/7/30.
 */
@Data
@Entity
@Table(name = "model_supplier")
public class Supplier extends BaseEntity {

    private String name;
    private String tel;
    private String email;
    private String link;
    private String qualification;
    private String main;
    private Integer level;
    private String address;
    private String contact;
    @OneToOne
    private Upload file;

}
