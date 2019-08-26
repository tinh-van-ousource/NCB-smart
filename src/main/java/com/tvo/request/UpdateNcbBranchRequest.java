package com.tvo.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateNcbBranchRequest {
	private String brnCode;

	private String branchName;

	private String departCode;

	private String departName;

	private String address;

	private String phone;

	private String fax;

	private String latitude;

	private String longitude;

	private String urlImg;

	private String dao;

	private String status;
}
