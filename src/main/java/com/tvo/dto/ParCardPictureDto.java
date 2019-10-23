package com.tvo.dto;

import java.util.Date;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParCardPictureDto {
    
	private String fileName;

	private String linkUrl;

	private String note;

	private String status;
}
