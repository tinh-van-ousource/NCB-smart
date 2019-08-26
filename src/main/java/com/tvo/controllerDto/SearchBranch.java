package com.tvo.controllerDto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SearchBranch {
private Long branchId;
	
	private String branchCode;
	
	private String branchName;

	private String cityCode;
	
	private String districtCode;
	
	private String branchAddress;
	
	private String status;
	
	private Date createdDate;
}
