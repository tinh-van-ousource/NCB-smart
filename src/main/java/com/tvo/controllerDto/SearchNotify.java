package com.tvo.controllerDto;

import java.sql.Date;

import com.tvo.dto.NotifyDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchNotify {

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

