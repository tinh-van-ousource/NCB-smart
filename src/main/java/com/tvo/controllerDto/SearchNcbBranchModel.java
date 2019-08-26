package com.tvo.controllerDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchNcbBranchModel {
	private String brnCode;

	private String branchName;

	private String departCode;

	private String departName;
	
	private String status;
}
