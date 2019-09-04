package com.tvo.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "NOTIFICATION_MBAPP")
@Getter
@Setter 	
public class Notify implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "PROVIDER")
	private String provider;
	@Id	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "ERROR")
	private String error;
	
	
	@Column(name = "MSG_CODE")
	private String msg_Code;
	
	@Column(name = "MSG_CODE_1")
	private String msg_Code_1;
	
	@Column(name = "MES_VN")
	private String mes_Vn;
	
	@Column(name = "MES_EN")
	private String mes_En;
	
	@Column(name = "USER_ID")
	private String user_Id;
	
	@Column(name = "CREATE_DATE")
	private Date create_Date;
	
}
