package com.tvo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePromotionsRequest {
	private String idName;
	
	private String type;
	
	private String functionType;
	
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
