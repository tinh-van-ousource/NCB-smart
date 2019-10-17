package com.tvo.controllerDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchNcbBannerModel {

	private String bannerCode;

	private String bannerName;

	private String status;

}
