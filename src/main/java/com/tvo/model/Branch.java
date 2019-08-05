/**
 * 
 */
package com.tvo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Ace
 *
 */
@Entity
@Table(name = "CMS_BRANCH")
@Getter
@Setter
public class Branch implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7110042333210180119L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AI_CMS_BRACNH_SQ")
	@Column(name = "BRANCH_ID")
	private Long branchId;
	
	@Column(name = "BRANCH_CODE")
	private String branchCode;
		
	@Column(name = "BRANCH_NAME")
	private String branchName;

	@Column(name = "CITY_CODE")
	private String cityCode;
	
	@Column(name = "DISTRICT_CODE")
	private String districtCode;
	
	@Column(name = "BRANCH_ADDRESS")
	private String branchAddress;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
}
