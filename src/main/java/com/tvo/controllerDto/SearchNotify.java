package com.tvo.controllerDto;

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
	
	
}

