package com.tvo.controllerDto;


import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class searchFunction {
private Long id;
	
	private String idName;
	
	private String typeFunction;
	
	private String function;
	
	private String status;
	
	private String quantity;
	
	private String cuType;
	
	private String ccy;
	
	private String limitDaily;
	
	private String limitFace;
	
	private String userId;
	
	private String min;
	
	private String max;
	
	private Date createDate;

	private String limitFinger;

	private String promotion;

	private String promotionName;

	private String percentage;

	private Date fromDate;

	private Date toDate;
}
