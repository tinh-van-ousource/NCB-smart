/**
 * 
 */
package com.tvo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Ace
 *
 */
@Entity
@Table(name = "CMS_TRANSACTION")
@Setter
@Getter
public class Transaction implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7015287597033169174L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AI_CMS_TRANS_SQ_GENERATOR")
    @SequenceGenerator(sequenceName = "AI_CMS_TRANS_SQ", allocationSize = 1, name = "AI_CMS_TRANS_SQ_GENERATOR")
	@Column(name = "TRANSACTION_ID")
	private Integer transactionId;
	
	@Column(name = "TRANSACTION_CODE")
	private String transactionCode;
	
	@Column(name = "TRANSACTION_NAME")
	private String transactionName;
	
	@Column(name = "STATUS")
	private String status;


	
	
}
