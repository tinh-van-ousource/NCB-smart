package com.tvo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_BANNER")
	@SequenceGenerator(sequenceName = "NCB_BANNER_SEQ", allocationSize = 1, name = "ID_BANNER")
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
