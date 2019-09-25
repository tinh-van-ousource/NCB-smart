package com.tvo.controllerDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotifyDto {
private String provider;
	
	private String type;
	
	private String error;
	
	private String msgCode;
	
	private String msg_Code_1;
	
	private String mes_Vn;
	
	private String mes_En;
	
	private Long user_Id;
}
