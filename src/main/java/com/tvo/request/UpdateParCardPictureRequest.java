package com.tvo.request;

import com.tvo.dto.ParCardPictureDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateParCardPictureRequest {
	private String fileName;

	private String linkUrl;

	private String note;

	private String status;
}
