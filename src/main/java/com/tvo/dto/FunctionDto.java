package com.tvo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FunctionDto {
	private Long id;

	private String prdName;

	private String tranType;

	private String typeId;

	private String status;

	private Long quantity;

	private String customerType;

	private String ccy;

	private Long limitDaily;

	private Long min;

	private Long max;

	private Long limitFaceid;

	private Long limitFinger;

	private String promotion;

	private String promotionName;

	private Long percentage;

	private Date fromDate;

	private Date toDate;

	private String createdBy;

	private Date createdDate;

	private String prd;
}
