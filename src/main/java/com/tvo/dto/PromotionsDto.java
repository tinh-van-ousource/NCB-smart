package com.tvo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PromotionsDto {

  	private String proCode;

    private String proName;

    private String status;

    private String proDes;
    
	@JsonFormat(pattern = "yyyy/M/d")
    private Date fromDate;
	
	@JsonFormat(pattern = "yyyy/M/d")
    private Date toDate;
	
	@JsonFormat(pattern = "yyyy/M/d")
    private Date createdDate;

    private String createdBy;

}
