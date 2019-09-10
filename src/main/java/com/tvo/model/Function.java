package com.tvo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "GROUP_FUNCTION_MBAPP")
@Getter
@Setter
public class Function implements Serializable {

	private static final long serialVersionUID = 2478521582208471030L;
	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "PRD_NAME")
	private String prdName;

	@Column(name = "TRAN_TYPE")
	private String tranType;

	@Column(name = "TYPE_ID")
	private String typeId;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "QUANTITY")
	private String quantity;

	@Column(name = "CUSTOMER_TYPE")
	private String customerType;

	@Column(name = "CCY")
	private String ccy;

	@Column(name = "LIMIT_DAILY")
	private Long limitDaily;

	@Column(name = "MIN")
	private Long min;

	@Column(name = "MAX")
	private Long max;

	@Column(name = "LIMIT_FACEID")
	private Long limitFaceid;

	@Column(name = "LIMIT_FINGER")
	private Long limitFinger;

	@Column(name = "PROMOTION")
	private String promotion;

	@Column(name = "PROMOTION_NAME")
	private String promotionName;

	@Column(name = "PERCENTAGE")
	private Long percentage;

	@Column(name = "FROM_DATE")
	private Date fromDate;

	@Column(name = "TO_DATE")
	private Date toDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "PRD")
	private String prd;

}
