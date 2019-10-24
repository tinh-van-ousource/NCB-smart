package com.tvo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class  CreateNotifyRequest {
private String provider;
	
	private String type; 
	
	private String error;
	
	private String msgCode;
	
	private String msg_Code_1;
	
	private String mes_Vn;
	
	private String mes_En;
	
	private Long user_Id;
	@JsonFormat(pattern = "yyyy/M/d")
	private Date create_Date;
}
