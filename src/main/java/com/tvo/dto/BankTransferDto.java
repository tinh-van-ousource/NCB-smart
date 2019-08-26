package com.tvo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankTransferDto {
	private String bankCode;

	private String bankName;
	
	private String shtname;

	private String status;

	private String bin;

	private String citad_gt;

	private String citad_tt;
}
