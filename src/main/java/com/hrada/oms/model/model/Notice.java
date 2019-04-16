package com.hrada.oms.model.model;

import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.common.Upload;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by shin on 2018/10/16.
 */
@Data
@Entity
@Table(name="model_notice")
public class Notice extends BaseEntity {

    @Temporal(TemporalType.DATE)
    private Date pubDate;
    private Integer state;
    private String title;
    @Lob
    private String content;
    @OneToOne
    private Upload file;

}
