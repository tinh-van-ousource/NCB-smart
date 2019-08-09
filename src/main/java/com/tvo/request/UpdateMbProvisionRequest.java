package com.tvo.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMbProvisionRequest {
	private Long id;

	private String provisionName;

	private String provisionLink;

	private String status;
}
