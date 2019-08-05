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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Ace
 *
 */
@Entity
@Table(name = "CMS_USER")
@Setter
@Getter
public class User extends AbstractEntity implements Serializable {


	private static final long serialVersionUID = 8922785714798336673L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AI_CMS_USER_SQ")
	@Column(name = "USER_ID", nullable = false)
	private Long userId;

	@Column(name = "USER_NAME", nullable = false)
	private String userName;
	
	@Column(name = "FULL_NAME", nullable = false)
	private String fullName;

	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	@Column(name = "BRANCH_CODE")
	private String branchCode;
	
	@Column(name = "TRANSACTION_CODE")
	private String transactionCode;
	
	@Column(name = "PASSCHANGE")
	private String passChange;
	
	@ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PHONE")
	private String phone;
	
	@Column(name = "COUNT_LOGIN_FAIL")
	private Integer countLoginFail;
	
	@Column(name = "USER_CODE")
	private String userCode;
}
