package com.tvo.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCityRequest {
private Long cityId;
	
	private String cityCode;

	private String cityName;

	private String status;
	@JsonFormat(pattern = "yyyy/M/d")
	private Date createDate;
}
