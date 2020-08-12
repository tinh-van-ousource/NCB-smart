package com.tvo.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author thanglt on 8/5/2020
 * @version 1.0
 */
@Entity
@Table(name = "QR_SERVICE")
@Data
public class QrServiceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_QR_SERVICE")
    @SequenceGenerator(sequenceName = "QR_SERVICE_SEQ", allocationSize = 1, name = "ID_QR_SERVICE")
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "SERVICE_TYPE")
    private String serviceType;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DELETED_AT")
    private Date deletedAt;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Column(name = "UPDATED_BY")
    private String updatedBy;
}
