/**
 * 
 */
package com.tvo.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ace
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchDto {
	
	private Long branchId;
	
	private String branchCode;
	
	private String branchName;

	private String cityCode;
	
	private String districtCode;
	
	private String branchAddress;
	
	private String status;
	
	private Date createdDate;
}
