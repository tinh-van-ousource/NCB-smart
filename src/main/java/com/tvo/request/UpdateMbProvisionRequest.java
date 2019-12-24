package com.tvo.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UpdateMbProvisionRequest {
	@NonNull
	private Long id;

	private String provisionName;

	private String provisionLink;
	
	private String provisionCode;

	private String status;
}
