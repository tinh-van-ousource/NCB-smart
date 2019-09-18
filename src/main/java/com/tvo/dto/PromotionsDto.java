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
    private Long id;

    private String tranType;

    private String typeId;

    private String customerType;

    private String promotionName;

    private String promotion;

    private String percentage;

    @JsonFormat(pattern = "yyyy-M-d")
    private Date fromDate;

    @JsonFormat(pattern = "yyyy-M-d")
    private Date toDate;

	private String prdName;

	private String status;

	private String createdBy;

    @JsonFormat(pattern = "yyyy-M-d")
	private Date createdDate;

}
