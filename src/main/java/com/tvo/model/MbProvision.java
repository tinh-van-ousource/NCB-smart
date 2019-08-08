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
@Table(name = "MB_PROVISION")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MbProvision {
	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "PROVISION_NAME")
	private String provisionName;

	@Column(name = "PROVISION_LINK")
	private String provisionLink;

	@Column(name = "STATUS")
	private String status;
}
