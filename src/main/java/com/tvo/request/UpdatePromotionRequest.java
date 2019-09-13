package com.tvo.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePromotionRequest {
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
