package com.tvo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFunctionRequest {

	private String id;

	private String prdName;

	private String tranType;

	private String typeId;

	private String status;

	private String quantity;

	private String customerType;

	private String ccy;

	private String limitDaily;

	private String min;

	private String max;

	private String limitFaceid;

	private String limitFinger;

	private String promotion;

	private String promotionName;

	private String percentage;

	private String fromDate;

	private String toDate;

	private String createdBy;

	private String createdDate;

	private String prd;
}
