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
@Table(name = "DAT_CFMAST")
@Getter
@Setter
@NoArgsConstructor
public class DatCfmast implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "RCDSTAT")
	private String rcdstat;
	@Column(name = "BNKID")
	private String bnkid;
	@Column(name = "BRNHOLD")
	private String brnhold;
	@Id
	@Column(name = "CIFNO")
	private String cifno;
	@Column(name = "NAME1")
	private String name1;
	@Column(name = "NAME2")
	private String name2;
	@Column(name = "SHTNAME")
	private String shtname;
	@Column(name = "TYPE")
	private String type;
	@Column(name = "IDNO")
	private String idno;
	@Column(name = "IDTYPE")
	private String idtype;
	@Column(name = "IDDATE")
	private String iddate;
	@Column(name = "IDPLACE")
	private String idplace;
	@Column(name = "STFCODE")
	private String stfcode;
	@Column(name = "ADRLINE1")
	private String adrline1;
	@Column(name = "ADRLINE2")
	private String adrline2;
	@Column(name = "BIRTHDAY")
	private String birthday;
	@Column(name = "PHONE")
	private String phone;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "FAXNO")
	private String faxno;
	@Column(name = "MAIL")
	private String mail;
	@Column(name = "TAXCODE")
	private String taxcode;
	@Column(name = "CHRMAN")
	private String chrman;
	@Column(name = "DIRNAME")
	private String dirname;
	@Column(name = "ACTNAME")
	private String actname;
	@Column(name = "BUSINESS")
	private String business;
	@Column(name = "SECTOR")
	private String sector;
	@Column(name = "GENDER")
	private String gender;
	@Column(name = "NATION")
	private String nation;
	@Column(name = "LOCATION")
	private String location;
	@Column(name = "ETHNIC")
	private String ethnic;
	@Column(name = "RELIGION")
	private String religion;
	@Column(name = "COUNTRY")
	private String country;
	@Column(name = "MSGLINE1")
	private String msgline1;
	@Column(name = "MSGLINE2")
	private String msgline2;
	@Column(name = "MSGLINE3")
	private String msgline3;
	@Column(name = "MSGLINE4")
	private String msgline4;
	@Column(name = "OFRCODE")
	private String ofrcode;
	@Column(name = "SUPCODE")
	private String supcode;
	@Column(name = "CRTDATE")
	private String crtdate;
	@Column(name = "LSTDATE")
	private String lstdate;
	@Column(name = "WRKSTN")
	private String wrkstn;
	@Column(name = "SDATE")
	private String sdate;
	@Column(name = "CFLIMIT")
	private String cflimit;
	@Column(name = "UTLAMT")
	private String utlamt;
	@Column(name = "AVLIMIT")
	private String avlimit;
	@Column(name = "PRCODE")
	private String prcode;
	@Column(name = "SPCOD01")
	private String spcod01;
	@Column(name = "SPCOD02")
	private String spcod02;
	@Column(name = "SPCOD03")
	private String spcod03;
	@Column(name = "SPCOD04")
	private String spcod04;
	@Column(name = "SPCOD05")
	private String spcod05;
	@Column(name = "SPCOD06")
	private String spcod06;
	@Column(name = "SPCOD07")
	private String spcod07;
	@Column(name = "SPCOD08")
	private String spcod08;
	@Column(name = "SPCOD09")
	private String spcod09;
	@Column(name = "SPCOD10")
	private String spcod10;
	@Column(name = "SPINF01")
	private String spinf01;
	@Column(name = "SPINF02")
	private String spinf02;
	@Column(name = "SPINF03")
	private String spinf03;
	@Column(name = "SPINF04")
	private String spinf04;
	@Column(name = "SPINF05")
	private String spinf05;
	@Column(name = "SPINF06")
	private String spinf06;
	@Column(name = "SPINF07")
	private String spinf07;
	@Column(name = "SPINF08")
	private String spinf08;
	@Column(name = "SPINF09")
	private String spinf09;
	@Column(name = "SPINF10")
	private String spinf10;
	@Column(name = "ODSTAT")
	private String odstat;
	@Column(name = "ODUSRID")
	private String odusrid;
	@Column(name = "ODSUPID")
	private String odsupid;
	@Column(name = "ODDATE")
	private String oddate;
	@Column(name = "PRIVDATE")
	private String privdate;
	@Column(name = "MSGUSR")
	private String msgusr;
	@Column(name = "PREUSR")
	private String preusr;
	@Column(name = "RSFLD01")
	private String rsfld01;
	@Column(name = "RSFLD02")
	private String rsfld02;
	@Column(name = "RSFLD03")
	private String rsfld03;
	@Column(name = "RSFLD04")
	private String rsfld04;
	@Column(name = "RSFLD05")
	private String rsfld05;
	@Column(name = "RSFLD06")
	private String rsfld06;
	@Column(name = "RSFLD07")
	private String rsfld07;
	@Column(name = "RSFLD08")
	private String rsfld08;
	@Column(name = "RSFLD09")
	private String rsfld09;
	@Column(name = "RSFLD10")
	private String rsfld10;
	@Column(name = "RSFLD11")
	private String rsfld11;
	@Column(name = "RSFLD12")
	private String rsfld12;
	@Column(name = "RSFLD13")
	private String rsfld13;
	@Column(name = "RSFLD14")
	private String rsfld14;
	@Column(name = "RSFLD15")
	private String rsfld15;
	@Column(name = "RSFLD16")
	private String rsfld16;
	@Column(name = "RSFLD17")
	private String rsfld17;
	@Column(name = "RSFLD18")
	private String rsfld18;
	@Column(name = "RSFLD19")
	private String rsfld19;
	@Column(name = "RSFLD20")
	private String rsfld20;
	@Column(name = "PRIVDATE1")
	private String privdate1;
	@Column(name = "RSNCODE")
	private String rsncode;
	@Column(name = "RSFLD21")
	private String rsfld21;
	@Column(name = "RSFLD22")
	private String rsfld22;
	@Column(name = "RSFLD23")
	private String rsfld23;
	@Column(name = "RSFLD24")
	private String rsfld24;
	@Column(name = "RSFLD25")
	private String rsfld25;
	@Column(name = "RSFLD26")
	private String rsfld26;
	@Column(name = "RSFLD27")
	private String rsfld27;
	@Column(name = "RSFLD28")
	private String rsfld28;
	@Column(name = "RSFLD29")
	private String rsfld29;
	@Column(name = "RSFLD30")
	private String rsfld30;

}
