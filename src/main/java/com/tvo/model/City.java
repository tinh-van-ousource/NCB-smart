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
@Table(name = "CMS_CITY")
@Getter
@Setter
public class City implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2478521582208471030L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AI_CMS_CITY_SQ")
	@Column(name = "CITY_ID", insertable = false)
	private Long cityId;

	@Column(name = "CITY_CODE")
	private String cityCode;

	@Column(name = "CITY_NAME")
	private String cityName;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

}
