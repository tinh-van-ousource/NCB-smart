/**
 * 
 */
package com.tvo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Ace
 *
 */
@Entity
@Table(name = "PAR_CARD_PRODUCT")
@Setter
@Getter
public class ParCardProduct implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1822861189401319401L;

	@Id
	@Column(name = "PRDCODE")
	private String prdcode;

	private String product;

	@Column(name = "ACTIVE_FEE")
	private String activeFee;

	private String cardtype;

	@Column(name = "CHANGESTT_FEE")
	private String changesttFee;

	@Column(name = "CLASS")
	private String class_;

	@Column(name = "DIRECTDD_FEE")
	private String directddFee;

	private String f01;

	private String f02;

	private String f03;

	private String f04;

	private String f05;

	@Column(name = "FILE_NAME")
	private String fileName;

	@Column(name = "ISSUE_FEE")
	private String issueFee;

	@Column(name = "LINK_ULR")
	private String linkUlr;

	@Column(name = "REISSUE_FEE")
	private String reissueFee;

	@Column(name = "REPIN_FEE")
	private String repinFee;

	private String status;
}
