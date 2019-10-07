package com.tvo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import javax.persistence.Column;

@Setter
@Getter
public class CreatePromotionsRequest {

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
