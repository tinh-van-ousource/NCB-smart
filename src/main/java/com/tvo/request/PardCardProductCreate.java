/**
 * 
 */
package com.tvo.request;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

/**
 * @author Ace
 *
 */
@Data
public class PardCardProductCreate {
	@NotEmpty
	private String prdcode;
	@NotEmpty
	private String product;

	private String activeFee;
	@NotEmpty
	private String cardtype;

	private String changesttFee;
	@NotEmpty
	private String class_;

	private String directddFee;

	private String f01;

	private String f02;

	private String f03;

	private String f04;

	private String f05;

	private String fileName;

	private String issueFee;

	private String linkUlr;

	private String reissueFee;

	private String repinFee;
	@NotEmpty
	private String status;
}
