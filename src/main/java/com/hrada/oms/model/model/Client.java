package com.hrada.oms.model.model;

import com.hrada.oms.model.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Created by shin on 2018/9/10.
 */
@Data
@Entity
@Table(name="model_client")
public class Client extends BaseEntity {

    private String name;
    private String tel;
    private String email;
    private String link;
    private String main;
    private String address;
    private String contact;
    @Lob
    private String content;
}
