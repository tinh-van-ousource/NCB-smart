package com.tvo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UpdatePromotionRequest {

	private String proCode;

    private String proName;

    private String status;

    private String proDes;
    
	@JsonFormat(pattern = "yyyy/M/d")
    private Date fromDate;
	
	@JsonFormat(pattern = "yyyy/M/d")
    private Date toDate;

    private String createdBy;

}
