package com.tvo.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Null;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "BANKCODE_MBAPP")
@Data
public class BankTransfer {


	@Id
	@Column(name = "BNK_CODE")
	private String bankCode;

	@Column(name = "BNKNAME")
	private String bankName;
	@Column(name = "SHTNAME")
	private String shtname;
	@Column(name = "STATUS")
	private String status;
	@Column(name = "BIN")
	private String bin;
	@Column(name = "CITAD_GT")
	private String citad_gt;
	@Column(name = "CITAD_TT")
	private String citad_tt;

}
