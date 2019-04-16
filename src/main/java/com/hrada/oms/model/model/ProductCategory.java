package com.hrada.oms.model.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrada.oms.model.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by shin on 2019-03-11.
 */
@Data
@Entity
@Table(name = "model_product_category")
public class ProductCategory extends BaseEntity {

    private String name;
    private String no;
    @OneToOne
    @JsonIgnore
    private ProductCategory parent;
    @OneToMany
    @JsonIgnore
    private Set<Product> products;
    private Integer state;
    private Integer pid;
}
