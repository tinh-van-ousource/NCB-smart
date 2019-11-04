package com.tvo.controllerDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchPrdPromotion {
	
	private String prd;

	private String proCode;

	private String status;
}
