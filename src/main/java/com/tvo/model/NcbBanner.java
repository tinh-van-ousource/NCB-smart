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
 * @date Aug 13, 2019
 */
@Entity
@Table(name = "NCB_BANNER")
@Getter
@Setter
@NoArgsConstructor
public class NcbBanner implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID")
	@SequenceGenerator(sequenceName = "NCB_BANNER_SEQ", allocationSize = 1, name = "ID")
	@Column(name = "ID")
	private Long id;

	@Column(name = "BANNER_CODE")
	private String bannerCode;

	@Column(name = "BANNER_NAME")
	private String bannerName;

	@Column(name = "LINK_IMG")
	private String linkImg;

	@Column(name = "LINK_URL_VN")
	private String linkUrlVn;

	@Column(name = "LINK_URL_EN")
	private String linkUrlEn;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "CREATE_DATE")
	private LocalDateTime createdDate;

}
