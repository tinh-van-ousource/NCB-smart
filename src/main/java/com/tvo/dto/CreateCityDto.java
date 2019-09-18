package com.tvo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCityDto {
	
	private String cityCode;

	private String cityName;

	private String status;
}
