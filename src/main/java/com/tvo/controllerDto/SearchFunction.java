package com.tvo.controllerDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchFunction {
	private Long id;
	
	private String prdName;

	private String tranType;

	private String typeId;

}
