package com.tvo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 7, 2019
 */
@Entity
@Table(name = "CONFIG_MBAPP")
@Getter
@Setter
@NoArgsConstructor
public class ParamManager implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name= "ID")
	private String id;
	
	@Column(name = "CODE")
	private String code;

	@Column(name = "NAME")
	private String name;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "DESCRIPTION")
	private String description;

}
