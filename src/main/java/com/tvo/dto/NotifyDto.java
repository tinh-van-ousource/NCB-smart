package com.tvo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyDto {
	private String provider;
	
	private String type;
	
	private String error;
	
	private String msgCode;
	
	private String msg_Code_1;
	
	private String mes_Vn;
	
	private String mes_En;
	
	private Long user_Id;
	
	private Date create_Date;
}
