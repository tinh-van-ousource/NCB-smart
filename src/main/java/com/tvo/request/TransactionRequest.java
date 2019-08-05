package com.tvo.request;

import lombok.Data;

@Data
public class TransactionRequest {
	private String transactionCode;
	
	private String transactionName;
	
	private String status;
}
