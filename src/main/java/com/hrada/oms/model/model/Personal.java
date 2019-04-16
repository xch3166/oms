package com.hrada.oms.model.model;

import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.common.Enum;
import com.hrada.oms.model.common.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by shin on 2018/3/30.
 */
@Getter
@Setter
@Entity
@Table(name="model_personal")
public class Personal extends BaseEntity implements Serializable {

    private String name;
    private Integer gender;
    private String tel;
    private String img;
    private String email;
    private String position;
    private String idCard;
    private String cardFront;
    private String cardBack;
    private String passPort;
    private String address;
    private String contact;
    private String contactTel;
    @Temporal(TemporalType.DATE)
    private Date entryTime;
    private String birthday;
    private String yy;
    private String mm;
    private String dd;
    //0:阴历；1：阳历；
    private Integer birthdayType=0;
    private Integer age;
    private String education;
    private Integer nature;
    private String certificate;
    private String school;
    private String major;
    private String diploma;
    private String degree;
    private String other;
    private String nationality;
    private String birthplace;
    private Integer account;
    private Integer marriage;
    private String contractPeriod;
    @Temporal(TemporalType.DATE)
    private Date contractStartDate;
    @Temporal(TemporalType.DATE)
    private Date contractEndDate;
    @Temporal(TemporalType.DATE)
    private Date startWorkingTime;
    @Lob
    private String workExperience;

    private Integer state;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    private Enum company;

    public Personal() {
    }

    public Personal(String name, Integer gender, String tel, String img, String email, String position, String idCard, String cardFront, String cardBack, String passPort, String address, String contact, String contactTel, Date entryTime, String birthday, String yy, String mm, String dd, Integer birthdayType, Integer age, String education, Integer nature, String certificate, String school, String major, String diploma, String degree, String other, String nationality, String birthplace, Integer account, Integer marriage, String contractPeriod, Date contractStartDate, Date contractEndDate, Date startWorkingTime, String workExperience, Integer state, User user, Enum company) {
        this.name = name;
        this.gender = gender;
        this.tel = tel;
        this.img = img;
        this.email = email;
        this.position = position;
        this.idCard = idCard;
        this.cardFront = cardFront;
        this.cardBack = cardBack;
        this.passPort = passPort;
        this.address = address;
        this.contact = contact;
        this.contactTel = contactTel;
        this.entryTime = entryTime;
        this.birthday = birthday;
        this.yy = yy;
        this.mm = mm;
        this.dd = dd;
        this.birthdayType = birthdayType;
        this.age = age;
        this.education = education;
        this.nature = nature;
        this.certificate = certificate;
        this.school = school;
        this.major = major;
        this.diploma = diploma;
        this.degree = degree;
        this.other = other;
        this.nationality = nationality;
        this.birthplace = birthplace;
        this.account = account;
        this.marriage = marriage;
        this.contractPeriod = contractPeriod;
        this.contractStartDate = contractStartDate;
        this.contractEndDate = contractEndDate;
        this.startWorkingTime = startWorkingTime;
        this.workExperience = workExperience;
        this.state = state;
        this.user = user;
        this.company = company;
    }
}
