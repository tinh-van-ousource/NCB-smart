package com.tvo.dto;

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

    private Date fromDate;

    private Date toDate;

	private String prdName;

	private String status;

	private String createdBy;

	private Date createdDate;

}
