package com.tvo.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 9, 2019
 */
@Entity
@Table(name = "NCB_FEEDBACK")
@Getter
@Setter
@NoArgsConstructor
public class NcbFeedback implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_FEEDBACK")
	@SequenceGenerator(sequenceName = "NCB_FEEDBACK_SEQ", allocationSize = 1, name = "ID_FEEDBACK")
	private Long id;

	@Column(name = "PRODUCT_CODE")
	private String productCode;

	@Column(name = "PRODUCT_NAME")
	private String productName;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "NAME")
	private String name;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	@Column(name = "STATUS")
	private String status;

}
