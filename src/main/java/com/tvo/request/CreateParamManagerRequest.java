package com.tvo.request;

import lombok.Data;

@Data
public class CreateParamManagerRequest {

	private String paramNo;

	private String paramName;

	private String paramValue;

	private String note;

	private String status;
}
