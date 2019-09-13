package com.tvo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNotifyRequest {
	
	private String provider;
	
	private String type;
	
	private String error;
	
	private String msg_Code;
	
	private String msg_Code_1;
	
	private String mes_Vn;
	
	private String mes_En;
	
	private String user_Id;
	@JsonFormat(pattern = "yyyy/M/d")
	private Date create_Date;
}
