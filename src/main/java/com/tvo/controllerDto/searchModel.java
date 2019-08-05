/**
 * 
 */
package com.tvo.controllerDto;

import java.util.Date;

import com.tvo.common.AppConstant;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Ace
 *
 */
@Setter
@Getter
public class searchModel {
	private String fullName;
	private String branchCode;
	private String transactionCode;
	private Integer roleId;
	private AppConstant.Status status;
	private Date fromDate;
	private Date toDate;
}
