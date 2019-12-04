package com.tvo.controllerDto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserSearchModel {
	private String fullName;
	private String branchCode;
	private String transactionCode;
	private String userName;
	private String roleName;
	private Date fromDate;
	private Date toDate;
}
