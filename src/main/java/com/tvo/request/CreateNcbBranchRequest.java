package com.tvo.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 8, 2019
 */
@Getter
@Setter
public class CreateNcbBranchRequest {
	private String brnCode;

	private String branchName;

	private String departCode;

	private String departName;

	private String address;

	private String phone;

	private String fax;

	private String latitude;

	private String longitude;

	private String urlImg;

	private String dao;

}
