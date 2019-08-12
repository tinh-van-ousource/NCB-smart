package com.tvo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "SERVICE_REGISTER_MBAPP_LOG")
@NoArgsConstructor
@Setter
@Getter
public class ServiceRegisterLogEntity implements Serializable {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SERVICE_REGISTER_ID")
    private Long serviceRegisterId;

    @Column(name = "TABLENAME")
    private String tableName;

    @Column(name = "COLUMNNAME")
    private String columnName;

    @Column(name = "OLDVALUE")
    private String oldValue;

    @Column(name = "NEWVALUE")
    private String newValue;

    @Column(name = "USERID")
    private String userId;

    @Column(name = "DATETIME")
    private LocalDateTime datetime;

}
