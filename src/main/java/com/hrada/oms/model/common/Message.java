package com.hrada.oms.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrada.oms.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by shin on 2018/11/7.
 */
@Entity
@Table(name = "common_message")
public class Message extends BaseEntity implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
    private String title;
    private String content;
    private Integer state=0;

    public Message() {
    }

    public Message(User user, String title, String content, Integer state) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
