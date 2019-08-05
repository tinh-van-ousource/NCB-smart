package com.tvo.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBranchRequest {
private Long branchId;
	
	private String branchCode;
	
	private String branchName;

	private String cityCode;
	
//	private String districtCode;
//	
//	private String branchAddress;
//	
//	private String status;
//	
//	private Date createdDate;
}
