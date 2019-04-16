package com.hrada.oms.model.log;

import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.model.Equipment;
import com.hrada.oms.model.model.Module;
import com.hrada.oms.model.model.Parts;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by shin on 2018/12/21.
 */
@Data
@Entity
@Table(name = "log_parts_record")
public class PartsRecord extends BaseEntity {

    @OneToOne
    private Parts parts;
    @OneToOne
    private Equipment equipment;
    @OneToOne
    private Module module;
    @Temporal(TemporalType.DATE)
    private Date sDate;
    @Temporal(TemporalType.DATE)
    private Date eDate;
    private Double hours=0.0;
}
