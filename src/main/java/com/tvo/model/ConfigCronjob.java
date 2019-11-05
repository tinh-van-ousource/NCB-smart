package com.tvo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CONFIG_CRONJOB")
@Getter
@Setter
public class ConfigCronjob implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "JOB_ID")
    private String jobId;

    @Column(name = "TIME_START")
    private String timeStart;

    @Column(name = "TIME_END")
    private String timeEnd;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "CREATED_BY")
    private String createdBy;
}
