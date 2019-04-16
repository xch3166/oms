package com.hrada.oms.model.log;

import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.model.Personal;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by shin on 2018/10/26.
 */
@Data
@Entity
@Table(name = "log_travel_peer")
public class TravelPeer extends BaseEntity {

    @OneToOne
    private Personal personal;
    private String dept;
    @ManyToOne
    private Travel travel;
}
