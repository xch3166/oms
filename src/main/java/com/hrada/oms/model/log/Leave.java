package com.hrada.oms.model.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.common.Enum;
import com.hrada.oms.model.model.Personal;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by shin on 2018/8/17.
 */
@Data
@Entity
@Table(name = "log_leave")
public class Leave extends BaseEntity {

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date endDate;
    @OneToOne
    private Enum type;
    @Lob
    private String content;
    @Lob
    private String reply;
    private Integer state=0;
    @OneToOne
    private Personal applicant;
    @OneToOne
    private Personal approver;
}
