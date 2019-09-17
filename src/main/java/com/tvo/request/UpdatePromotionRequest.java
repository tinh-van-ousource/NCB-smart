package com.tvo.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePromotionRequest {
private Long id;
	
	private String tranType;
	
	private String typeId;
 
	private String customerType;
	
	private String promotionName;

	private String promotion;
	
	private String percentage;
	@JsonFormat(pattern = "yyyy/M/d")
	private Date fromDate;
	@JsonFormat(pattern = "yyyy/M/d")
	private Date toDate;
}
