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
@Table(name = "NOTIFICATION_MBAPP")
@Getter
@Setter 	
public class Notify implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "PROVIDER")
	private String provider;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "ERROR")
	private String error;
	
	@Id
	@Column(name = "MSG_CODE")
	private String msgCode;
	
	@Column(name = "MSG_CODE_1")
	private String msg_Code_1;
	
	@Column(name = "MES_VN")
	private String mes_Vn;	
	
	@Column(name = "MES_EN")
	private String mes_En;
	
	@Column(name = "USER_ID")
	private Long user_Id;
	
	@Column(name = "CREATE_DATE")
	private Date create_Date;
	
}
