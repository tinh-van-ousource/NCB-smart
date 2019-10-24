package com.tvo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PROMOTION_MBAPP")
@Getter
@Setter
public class Promotions {

    @Id
    @Column(name = "PRO_CODE")
    private String proCode;

    @Column(name = "PRO_NAME")
    private String proName;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PRO_DES")
    private String proDes;

    @Column(name = "FROM_DATE")
    private Date fromDate;

    @Column(name = "TO_DATE")
    private Date toDate;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy;
}
