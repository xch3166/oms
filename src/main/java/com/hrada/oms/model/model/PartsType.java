package com.hrada.oms.model.model;

import javax.persistence.*;

/**
 * Created by shin on 2018/8/9.
 */
@Entity
@Table(name = "model_parts_type")
public class PartsType {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer state;
    @OneToOne
    private PartsType parent;
    @ManyToOne
    private Parts parts;

    public PartsType() {
    }

    public PartsType(String name, Integer state, PartsType parent, Parts parts) {
        this.name = name;
        this.state = state;
        this.parent = parent;
        this.parts = parts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public PartsType getParent() {
        return parent;
    }

    public void setParent(PartsType parent) {
        this.parent = parent;
    }

    public Parts getParts() {
        return parts;
    }

    public void setParts(Parts parts) {
        this.parts = parts;
    }
}
