package com.hrada.oms.model.log;

import com.hrada.oms.model.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by shin on 2019/1/3.
 */
@Data
@Entity
@Table(name = "log_change_record")
public class ChangeRecord extends BaseEntity {

    @ManyToOne
    private AttendanceLog attendanceLog;
    //0：去；1：返回；
    private Integer type;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String remark;
}
