package com.tvo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 8, 2019
 */
@Entity
@Table(name = "NCB_BRANCH")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NcbBranch {

	@Column(name = "BRN_CODE")
	private String brnCode;

	@Column(name = "BRANCH_NAME")
	private String branchName;

	@Id
	@Column(name = "DEPART_CODE")
	private String departCode;

	@Column(name = "DEPART_NAME")
	private String departName;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "FAX")
	private String fax;

	@Column(name = "LATITUDE")
	private String latitude;

	@Column(name = "LONGITUDE")
	private String longitude;

	@Column(name = "URL_IMG")
	private String urlImg;

	@Column(name = "DAO")
	private String dao;

	@Column(name = "STATUS")
	private String status;
}
