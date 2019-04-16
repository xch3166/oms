package com.hrada.oms.model.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrada.oms.model.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * Created by shin on 2018/4/8.
 */
@Data
@ToString(exclude = "partsList")
@Entity
@Table(name="model_module")
public class Module extends BaseEntity {


    @ManyToOne
    private Base base;
    @ManyToOne
    private Equipment equipment;
    private String name;
    private String no;
    private Integer length;
    private Integer width;
    private Integer height;
    private Double weight;
    private Double power;
    private Integer state;
    @ManyToMany
    @JoinTable(
        name="model_module_parts",
        joinColumns=@JoinColumn(name="module_id", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="parts_id", referencedColumnName="id")
    )
    @JsonIgnore
    private List<Parts> partsList;
}
