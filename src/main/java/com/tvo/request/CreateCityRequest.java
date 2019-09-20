package com.tvo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCityRequest {
	private Long cityId;
	
	private String cityCode;

	private String cityName;

	private String status;
	@JsonFormat(pattern = "yyyy/M/d")
	private Date createDate;
	
}
