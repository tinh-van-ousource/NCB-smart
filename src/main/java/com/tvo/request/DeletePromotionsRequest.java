package com.tvo.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class DeletePromotionsRequest {

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
