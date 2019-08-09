package com.tvo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 9, 2019
 */
@Entity
@Table(name = "NCB_GUIDELINE")
@Getter
@Setter
public class NcbGuideline extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "SERVICE_ID")
	private String serviceId;

	@Column(name = "CONTENT")
	private String content;

}
