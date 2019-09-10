package com.tvo.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFunctionRequest {
	private Long id;

	private String prdName;

	private String tranType;

	private String typeId;

	private String status;

	private String quantity;

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
