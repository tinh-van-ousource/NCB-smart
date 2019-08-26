package com.tvo.dto;

import com.tvo.model.DatCfmast;
import com.tvo.model.Function;
import lombok.Data;

import java.util.Date;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Jul 31, 2019
 */
@Data
public class DatUserProfileDto {
	private String bnkid;

	private String brncode;

	private String officecode;

	private String crtusrid;

	private String usrid;

	private String usrpwd;

	private String usrfname;

	private String usrsname;

	private String cifgrp;

	private String cifname;

	private String pwdlevela;

	private String pwdlevelb;

	private String pwdlevelc;

	private String pwdleveld;

	private String pwdlevele;

	private String pwdlevelf;

	private String pwdlevelg;

	private String pwdlevelh;

	private String remark1;

	private String remark2;

	private String remark3;

	private String remark4;

	private String remark5;

	private String usrlock;

	private String usrstatus;

	private String usrip;

	private String usrhost;

	private Date tmrlogin;

	private Date tmrlogout;

	private String mfid00;

	private String mfid01;

	private String mfid02;

	private String mfid04;

	private String mfid05;

	private String mfid03;

	private String mfid06;

	private String mfid07;

	private String mfid08;

	private String mfid09;

	private String sfid30;

	private String sfid31;

	private String sfid32;

	private String sfid33;

	private String sfid34;

	private String sfid35;

	private String sfid36;

	private String sfid37;

	private String sfid38;

	private String sfid39;

	private String createby;

	private String canCreateuser;

	private String ibpmt;

	private String cfrtype;

	private String email;

	private String mobile;

	private String lm4rm;

	private String newstart;

	private long loginfailcnt;

	private String chgpass;

	private String md5pwd;

	private String touchidLogin;

	private String touchidTransfer;

	private String touchid;

	private String touchidTran;

	private Date tmrchangepass;

	private Function function;

	private DatCfmast datCfmast;
}
