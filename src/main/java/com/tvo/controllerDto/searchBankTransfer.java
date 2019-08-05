package com.tvo.controllerDto;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class searchBankTransfer {
	private String bankCode;

	private String bankName;
	
	private String shtname;
	
	private String status;
	
	private String bin;
	
	private String citad_gt;
	
	private String citad_tt;

}
