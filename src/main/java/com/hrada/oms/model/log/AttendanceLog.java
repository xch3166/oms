package com.hrada.oms.model.log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.model.Base;
import com.hrada.oms.model.model.Personal;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by shin on 2018/4/19.
 */
@Data
@Entity
@Table(name = "log_attendance")
public class AttendanceLog extends BaseEntity {

    @Temporal(TemporalType.DATE)
    private Date sDate;
    @Temporal(TemporalType.DATE)
    private Date eDate;
    @OneToOne
    private Base base;
    @OneToOne
    private Personal personal;
    @OneToOne
    private Personal succession;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="attendance_id")
    @JsonIgnore
    private List<ChangeRecord> records;
}
