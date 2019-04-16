package com.hrada.oms.model.log;

import com.hrada.oms.model.model.Base;
import com.hrada.oms.model.model.Personal;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by shin on 2018/8/2.
 */
@Entity
@Table(name = "log_safe_training")
public class SafetyTraining {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Base base;
    @OneToOne
    private Personal personal;
    @Temporal(TemporalType.DATE)
    private Date logDate;
    private String title;
    private String content;

    public SafetyTraining() {
    }

    public SafetyTraining(Base base, Personal personal, Date logDate, String title, String content) {
        this.base = base;
        this.personal = personal;
        this.logDate = logDate;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
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
}
