package com.tvo.controllerDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchFunction {
	
	private String prd;

	private String tranType;

	private String typeId;
	
	private String status;

}
