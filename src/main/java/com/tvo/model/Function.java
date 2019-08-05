package com.tvo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "GROUP_FUNCTION_MBAPP")
@Getter
@Setter
public class Function implements Serializable {

	private static final long serialVersionUID = 2478521582208471030L;
	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "ID_NAME")
	private String idName;

	@Column(name = "TYPE_FUNCTION")
	private String typeFunction;

	@Column(name = "FUNCTION")
	private String function;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "QUANTITY")
	private String quantity;

	@Column(name = "CU_TYPE")
	private String cuType;

	@Column(name = "CCY")
	private String ccy;

	@Column(name = "LIMIT_DAILY")
	private String limitDaily;

	@Column(name = "LIMIT_FACE")
	private String limitFace;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "MIN")
	private String min;

	@Column(name = "MAX")
	private String max;

	@Column(name = "CREATE_DATE")
	private Date createDate;

	@Column(name = "LIMIT_FINGER")
	private String limitFinger;

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

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "function")
//	private List<DatUserProfile> listUser;

}
