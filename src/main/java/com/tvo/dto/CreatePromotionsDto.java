package com.tvo.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePromotionsDto {
	private String proCode;
	
    private String proName;
    
    private String status;
    
    private String proDes;
    
	@JsonFormat(pattern = "yyyy/M/d")
    private Date fromDate;
	
	@JsonFormat(pattern = "yyyy/M/d")
    private Date toDate;

    private Date createdDate;

    private String createdBy;
}
