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
@Table(name = "NCB_GUIDELINE")
@Getter
@Setter
@NoArgsConstructor
public class NcbGuideline implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GUIDELINE")
	@SequenceGenerator(sequenceName = "NCB_GUIDELINE_SEQ", allocationSize = 1, name = "ID_GUIDELINE")
	private Long id;

	@Column(name = "SERVICE_ID")
	private String serviceId;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	@Column(name = "STATUS")
	private String status;

}
