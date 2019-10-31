package com.tvo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DAT_USERPROFILE")
@Getter
@Setter
@NoArgsConstructor
public class DatUserProfile implements Serializable {

    private static final long serialVersionUID = 2478521582208471030L;

    @Column(name = "BNKID")
    private String bnkid;

    @Column(name = "BRNCODE")
    private String brncode;

    @Column(name = "OFFICECODE")
    private String officecode;

    @Column(name = "CRTUSRID")
    private String crtusrid;

    @Id
    @Column(name = "USRID")
    private String usrid;

    @Column(name = "USRPWD")
    private String usrpwd;

    @Column(name = "USRFNAME")
    private String usrfname;

    @Column(name = "USRSNAME")
    private String usrsname;

    @Column(name = "CIFGRP", insertable = false, updatable = false)
    private String cifgrp;

    @Column(name = "CIFNAME")
    private String cifname;

    @Column(name = "PWDLEVELA")
    private String pwdlevela;

    @Column(name = "PWDLEVELB")
    private String pwdlevelb;

    @Column(name = "PWDLEVELC")
    private String pwdlevelc;

    @Column(name = "PWDLEVELD")
    private String pwdleveld;

    @Column(name = "PWDLEVELE")
    private String pwdlevele;

    @Column(name = "PWDLEVELF")
    private String pwdlevelf;

    @Column(name = "PWDLEVELG")
    private String pwdlevelg;

    @Column(name = "PWDLEVELH")
    private String pwdlevelh;

    @Column(name = "REMARK1")
    private String remark1;

    @Column(name = "REMARK2")
    private String remark2;

    @Column(name = "REMARK3")
    private String remark3;

    @Column(name = "REMARK4")
    private String remark4;

    @Column(name = "REMARK5")
    private String remark5;

    @Column(name = "USRLOCK")
    private String usrlock;

    @Column(name = "USRSTATUS")
    private String usrstatus;

    @Column(name = "USRIP")
    private String usrip;

    @Column(name = "USRHOST")
    private String usrhost;

    @Column(name = "TMRLOGIN")
    private Date tmrlogin;

    @Column(name = "TMRLOGOUT")
    private Date tmrlogout;

    @Column(name = "MFID_00")
    private String mfid00;

    @Column(name = "MFID_01")
    private String mfid01;

    @Column(name = "MFID_02")
    private String mfid02;

    @Column(name = "MFID_04")
    private String mfid04;

    @Column(name = "MFID_05")
    private String mfid05;

    @Column(name = "MFID_03")
    private String mfid03;

    @Column(name = "MFID_06")
    private String mfid06;

    @Column(name = "MFID_07")
    private String mfid07;

    @Column(name = "MFID_08")
    private String mfid08;

    @Column(name = "MFID_09")
    private String mfid09;

    @Column(name = "SFID_30")
    private String sfid30;

    @Column(name = "SFID_31")
    private String sfid31;

    @Column(name = "SFID_32")
    private String sfid32;

    @Column(name = "SFID_33")
    private String sfid33;

    @Column(name = "SFID_34")
    private String sfid34;

    @Column(name = "SFID_35")
    private String sfid35;

    @Column(name = "SFID_36")
    private String sfid36;

    @Column(name = "SFID_37")
    private String sfid37;

    @Column(name = "SFID_38")
    private String sfid38;

    @Column(name = "SFID_39")
    private String sfid39;

    @Column(name = "CREATEBY")
    private String createby;

    @Column(name = "CAN_CREATEUSER")
    private String canCreateuser;

    @Column(name = "IBPMT")
    private String ibpmt;

    @Column(name = "CFRTYPE")
    private String cfrtype;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "LM4RM", insertable = false, updatable = false)
    private String lm4rm;

    @Column(name = "NEWSTART")
    private String newstart;

    @Column(name = "LOGINFAILCNT")
    private Long loginfailcnt;

    @Column(name = "CHGPASS")
    private String chgpass;

    @Column(name = "MD5PWD")
    private String md5pwd;

    @Column(name = "TOUCHID_LOGIN")
    private String touchidLogin;

    @Column(name = "TOUCHID_TRANSFER")
    private String touchidTransfer;

    @Column(name = "TOUCHID")
    private String touchid;

    @Column(name = "TOUCHID_TRAN")
    private String touchidTran;

    @Column(name = "TMRCHANGEPASS")
    private Date tmrchangepass;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumns({@JoinColumn(name = "LM4RM", referencedColumnName = "ID")})
    private Function function;

    @ManyToOne
//    (fetch = FetchType.LAZY)
//    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumns({@JoinColumn(name = "CIFGRP", referencedColumnName = "CIFNO")})
    private DatCfmast datCfmast;
}
