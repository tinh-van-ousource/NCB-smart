package com.tvo.controllerDto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class searchBranch {
private Long branchId;
	
	private String branchCode;
	
	private String branchName;

	private String cityCode;
	
	private String districtCode;
	
	private String branchAddress;
	
	private String status;
	
	private Date createdDate;
}
