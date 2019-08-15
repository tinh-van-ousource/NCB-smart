package com.tvo.model;

import java.io.Serializable;

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
 * @date Aug 8, 2019
 */
@Entity
@Table(name = "MB_PROVISION")
@Setter
@Getter
@NoArgsConstructor
public class MbProvision implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_PROVISION")
    @SequenceGenerator(sequenceName = "MB_PROVISION_SEQ", allocationSize = 1, name = "ID_PROVISION")
	@Column(name = "ID")
	private Long id;

	@Column(name = "PROVISION_NAME")
	private String provisionName;

	@Column(name = "PROVISION_LINK")
	private String provisionLink;

	@Column(name = "STATUS")
	private String status;
}
