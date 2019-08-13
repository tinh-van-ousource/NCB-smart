package com.tvo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 13, 2019
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class NcbBannerDto {
	private String id;

	private String bannerCode;

	private String bannerName;

	private String linkImg;

	private String linkUrlVn;

	private String linkUrlEn;

	private String status;

	private String createdDate;
}
