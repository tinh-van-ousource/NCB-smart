package com.tvo.request;

import com.tvo.dto.ParCardPictureDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParCardPictureSearchDto {
	private String fileName;

	private String status;
}
