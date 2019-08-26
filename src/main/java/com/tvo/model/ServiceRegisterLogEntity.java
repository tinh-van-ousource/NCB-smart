package com.tvo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "SERVICE_REGISTER_MBAPP_LOG")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ServiceRegisterLogEntity implements Serializable {

    private static final long serialVersionUID = 6479602175532593444L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERVICE_REGISTER_LOG_ID_SQ_GENERATOR")
    @SequenceGenerator(sequenceName = "SERVICE_REGISTER_LOG_ID_SQ", allocationSize = 1, name = "SERVICE_REGISTER_LOG_ID_SQ_GENERATOR")
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
