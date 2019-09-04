package com.tvo.dto;

import java.util.Date;

import com.tvo.request.CreateNotifyRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotifyDto {
	private String provider;
	
	private String type;
	
	private String error;
	
	private String msg_Code;
	
	private String msg_Code_1;
	
	private String mes_Vn;
	
	private String mes_En;
	
	private String user_Id;
	
	private Date create_Date;
}
