package com.tvo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePromotionsRequest {
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
