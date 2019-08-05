/**
 * 
 */
package com.tvo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ace
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
	
	private Integer transactionId;
	
	private String transactionCode;
	
	private String transactionName;
	
	private String status;
	
}
