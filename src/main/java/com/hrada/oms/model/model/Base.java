package com.hrada.oms.model.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by shin on 2018/3/30.
 */
@Entity
@Table( name = "model_base")
public class Base {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address;
    private String tel;
    @OneToOne
    private Personal manager;
    @OneToOne
    private Personal sManager;
    @Temporal(TemporalType.DATE)
    private Date sDate;
    private Integer state;

    @OneToMany
    private List<Well> wells;

    public Base() {
    }

    public Base(String name, String address, String tel, Personal manager, Personal sManager, Date sDate, Integer state, List<Well> wells) {
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.manager = manager;
        this.sManager = sManager;
        this.sDate = sDate;
        this.state = state;
        this.wells = wells;
    }

    public List<Well> getWells() {
        return wells;
    }

    public void setWells(List<Well> wells) {
        this.wells = wells;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Personal getManager() {
        return manager;
    }

    public void setManager(Personal manager) {
        this.manager = manager;
    }

    public Personal getsManager() {
        return sManager;
    }

    public void setsManager(Personal sManager) {
        this.sManager = sManager;
    }

    public Date getsDate() {
        return sDate;
    }

    public void setsDate(Date sDate) {
        this.sDate = sDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
