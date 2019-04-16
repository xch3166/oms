package com.hrada.oms.model.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrada.oms.model.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by shin on 2018/4/8.
 */
@Data
@Entity
@Table(name="model_equipment")
public class Equipment extends BaseEntity {

    @ManyToOne
    private Base base;
    @ManyToOne
    private Well well;
    private String name;
    private String no;
    private Integer length;
    private Integer width;
    private Integer height;
    private Double weight;
    private Double power;
    private Integer state;
    private Integer hours;

    @ManyToMany
    @JoinTable(
        name="model_equipment_parts",
        joinColumns=@JoinColumn(name="equipment_id", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="parts_id", referencedColumnName="id")
    )
    @JsonIgnore
    private List<Parts> partsList;
}
