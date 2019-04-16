package com.hrada.oms.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by shin on 2017/9/13.
 */
@Entity
@Table(name = "common_permission")
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer flag;

    @ManyToMany
    @JoinTable(
            name="common_role_permission",
            joinColumns=@JoinColumn(name="permission_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="role_id", referencedColumnName="id")
    )
    @JsonIgnore
    private List<Role> roleList;

    public Permission() {
    }

    public Permission(String name, Integer flag, List<Role> roleList) {
        this.name = name;
        this.flag = flag;
        this.roleList = roleList;
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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}