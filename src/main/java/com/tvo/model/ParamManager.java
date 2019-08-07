package com.tvo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 7, 2019
 */
@Entity
@Table(name = "PARAM_MANAGER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParamManager implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PARAM_NO")
	private String paramNo;

	@Column(name = "PARAM_NAME")
	private String paramName;

	@Column(name = "PARAM_VALUE")
	private String paramValue;

	@Column(name = "NOTE")
	private String note;

	@Column(name = "STATUS")
	private String status;

}
