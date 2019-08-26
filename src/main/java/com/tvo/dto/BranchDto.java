/**
 * 
 */
package com.tvo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
