package com.hrada.oms.model.log;

import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.common.Enum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by shin on 2018/10/26.
 */
@Data
@Entity
@Table(name = "log_travel_stroke")
public class TravelStroke extends BaseEntity {

    @Temporal(TemporalType.DATE)
    private Date date;
    private String departure;
    private String destination;
    @OneToOne
    private Enum type;
    @ManyToOne
    private Travel travel;
}
