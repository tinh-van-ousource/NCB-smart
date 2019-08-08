package com.tvo.request;

import lombok.Data;

@Data
public class CreateMbProvisionRequest {
	private String id;

	private String provisionName;

	private String provisionLink;

	private String status;
}
