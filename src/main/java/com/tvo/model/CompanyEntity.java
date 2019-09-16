package com.tvo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "COMPANY")
@NoArgsConstructor
@Setter
@Getter
public class CompanyEntity implements Serializable {

    private static final long serialVersionUID = 7003030148868469074L;

    @Id
    @Column(name = "COMP_CODE")
    private String compCode;

    @Column(name = "COMP_NAME")
    private String compName;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "DAO")
    private String dao;

    @Column(name = "MCN")
    private String mcn;

    @Column(name = "MP")
    private String mp;

}
