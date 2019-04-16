package com.hrada.oms.model.model;

import javax.persistence.*;

/**
 * Created by shin on 2018/4/8.
 */
@Entity
@Table(name="model_well")
public class Well {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private Integer type;
    private Integer depth;
    private Integer maxPressure;
    private Integer total;
    private Integer state;

    @ManyToOne
    private Base base;

    public Well() {
    }

    public Well(String name, Integer state, Integer type, Integer depth, Integer maxPressure, Integer total, Base base) {
        this.name = name;
        this.state = state;
        this.type = type;
        this.depth = depth;
        this.maxPressure = maxPressure;
        this.total = total;
        this.base = base;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getMaxPressure() {
        return maxPressure;
    }

    public void setMaxPressure(Integer maxPressure) {
        this.maxPressure = maxPressure;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }
}
