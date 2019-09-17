package com.tvo.dto;

import java.util.Date;

import com.tvo.controllerDto.CreateNotifyDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCityDto {
	private Long cityId;
	
	private String cityCode;

	private String cityName;

	private String status;
}
