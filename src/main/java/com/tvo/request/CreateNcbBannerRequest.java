package com.tvo.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 13, 2019
 */
@Getter
@Setter
public class CreateNcbBannerRequest {

	private String bannerCode;

	private String bannerName;

	private String linkImg;

	private String linkUrlVn;

	private String linkUrlEn;

}
