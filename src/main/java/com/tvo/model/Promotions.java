package com.tvo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "GROUP_FUNCTION_MBAPP")
@Getter
@Setter
public class Promotions {
		@Id
		@Column(name = "ID")
		private Long id;
		
		@Column(name = "TRAN_TYPE")
		private String tranType;
		
		@Column(name = "TYPE_ID")
		private String typeId;
	
		@Column(name = "CUSTOMER_TYPE")
		private String customerType;
		
		@Column(name = "PROMOTION")
		private String promotion;
	
		@Column(name = "PROMOTION_NAME")
		private String promotionName;
	
		@Column(name = "PERCENTAGE")
		private String percentage;
	
		@Column(name = "FROM_DATE")
		private Date fromDate;
	
		@Column(name = "TO_DATE")
		private Date toDate;

}
