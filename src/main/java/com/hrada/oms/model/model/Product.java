package com.hrada.oms.model.model;

import com.hrada.oms.model.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by shin on 2019-03-11.
 */
@Data
@Entity
@Table(name = "model_product")
public class Product extends BaseEntity {

    private String name;
    private String no;
    private String type;
    private String spec;
    private String manufacturer;
    private String remark;
    private String unit;
    @OneToOne
    private ProductCategory category;
}
