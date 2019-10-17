package com.tvo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFunctionRequest {

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

	@JsonFormat(pattern = "yyyy/M/d")
	private Date fromDate;

	@JsonFormat(pattern = "yyyy/M/d")
	private Date toDate;

	private String createdBy;

	@JsonFormat(pattern = "yyyy/M/d")
	private Date createdDate;

	private String prd;
}
