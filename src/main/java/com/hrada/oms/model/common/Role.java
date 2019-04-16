package com.hrada.oms.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by shin on 2017/9/13.
 */
@Entity
@Table(name = "common_role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer flag;
    private Integer state;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name="common_role_permission",
        joinColumns=@JoinColumn(name="role_id", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="permission_id", referencedColumnName="id")
    )
    Set<Permission> permissionList;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(
        name="common_user_role",
        joinColumns=@JoinColumn(name="role_id", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="user_id", referencedColumnName="id")
    )
    @JsonIgnore
    private Set<User> userList;

    public Role() {
    }

    public Role(String name, Integer flag, Integer state, Set<Permission> permissionList, Set<User> userList) {
        this.name = name;
        this.flag = flag;
        this.state = state;
        this.permissionList = permissionList;
        this.userList = userList;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Set<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(Set<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public Set<User> getUserList() {
        return userList;
    }

    public void setUserList(Set<User> userList) {
        this.userList = userList;
    }
}