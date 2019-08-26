/**
 * 
 */
package com.tvo.controllerDto;

import com.tvo.common.AppConstant;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Ace
 *
 */
@Setter
@Getter
public class SearchModel {
	private String fullName;
	private String branchCode;
	private String transactionCode;
	private Integer roleId;
	private AppConstant.Status status;
	private Date fromDate;
	private Date toDate;
}
