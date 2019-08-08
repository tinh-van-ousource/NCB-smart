package com.tvo.controllerDto;

import lombok.Data;

@Data
public class SearchNcbBranchModel {
	private String brnCode;

	private String branchName;

	private String departCode;

	private String departName;
	
	private String status;
}
