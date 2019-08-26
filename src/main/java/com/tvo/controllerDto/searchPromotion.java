package com.tvo.controllerDto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class searchPromotion {
	private Long id;
	
	private String type;
	
	private String functionType;

	private String cuType;
	
	private String promotion;

	private String promotionName;

	private String percentage;

	private Date fromDate;

	private Date toDate;
}
