package com.hrada.oms.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.model.Personal;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by shin on 2017/9/13.
 */
@Data
@Entity
@Table(name = "common_user")
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8366929034564774130L;

    @Column(unique=true)
    private String uname;
    private String upass;
    private String name;
    private Integer state;

    @OneToOne
    @JsonIgnore
    private Personal personal;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(
        name="common_user_role",
        joinColumns=@JoinColumn(name="user_id", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="role_id", referencedColumnName="id")
    )
    private Set<Role> roleList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Message> messageList;
}