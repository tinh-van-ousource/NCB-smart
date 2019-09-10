package com.tvo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePromotionsRequest {
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
