package com.tvo.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UpdateParamManagerRequest {

	@NonNull
	private String paramNo;

	private String paramName;

	private String paramValue;

	private String note;

	private String status;

}
