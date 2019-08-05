/**
 * 
 */
package com.tvo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AI_CMS_TRANS_SQ")
	@Column(name = "TRANSACTION_ID")
	private Integer transactionId;
	
	@Column(name = "TRANSACTION_CODE")
	private String transactionCode;
	
	@Column(name = "TRANSACTION_NAME")
	private String transactionName;
	
	@Column(name = "STATUS")
	private String status;


	
	
}
