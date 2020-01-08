package com.tvo.controllerDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchNotify {

	private String provider; 
	
	private String msgCode;
	
	private String error;
	private String type;
	
	private String mes_Vn;
	
	
}

