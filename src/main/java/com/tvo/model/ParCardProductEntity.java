package com.tvo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "PAR_CARD_PRODUCT")
@Setter
@Getter
@NoArgsConstructor
public class ParCardProductEntity implements Serializable {

	private static final long serialVersionUID = 1822861189401319401L;

	@Id
	@Column(name = "PRDCODE")
	private String prdcode;

	@Column(name = "PRODUCT")
	private String product;

	@Column(name = "CLASS")
	private String class_;

	@Column(name = "CARDTYPE")
	private String cardtype;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "ISSUE_FEE")
	private String issueFee;

	@Column(name = "REISSUE_FEE")
	private String reissueFee;

	@Column(name = "REPIN_FEE")
	private String repinFee;

	@Column(name = "ACTIVE_FEE")
	private String activeFee;

	@Column(name = "CHANGESTT_FEE")
	private String changesttFee;

	@Column(name = "DIRECTDD_FEE")
	private String directddFee;

	@Column(name = "F01")
	private String f01;

	@Column(name = "F02")
	private String f02;

	@Column(name = "F03")
	private String f03;

	@Column(name = "F04")
	private String f04;

	@Column(name = "F05")
	private String f05;

	@Column(name = "LINK_ULR")
	private String linkUrl;

	@Column(name = "FILE_NAME")
	private String fileName;

}
