package com.tvo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBankTransferRequest {
	private String bankCode;

	private String bankName;
	
	private String shtname;
	
	private String status;
	
	private String bin;
	
	private String citad_gt;
	
	private String citad_tt;
}
