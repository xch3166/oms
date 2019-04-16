package com.hrada.oms.model.log;

import com.hrada.oms.model.model.Base;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by shin on 2018/4/19.
 */
@Entity
@Table(name = "log_accident")
public class Accident {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Base base;
    @Temporal(TemporalType.DATE)
    private Date logDate;
    @Lob
    private String content;
    private String file;

    public Accident() {
    }

    public Accident(Base base, Date logDate, String content, String file) {
        this.base = base;
        this.logDate = logDate;
        this.content = content;
        this.file = file;
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

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
