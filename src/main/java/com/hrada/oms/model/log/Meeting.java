package com.hrada.oms.model.log;

import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.common.Upload;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by shin on 2019/1/8.
 */
@Data
@Entity
@Table(name = "log_meeting")
public class Meeting extends BaseEntity {

    private String title;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String content;
    @OneToOne
    private Upload file;
}
