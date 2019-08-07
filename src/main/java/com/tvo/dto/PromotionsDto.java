package com.tvo.dto;

import java.util.Date;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PromotionsDto {
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
