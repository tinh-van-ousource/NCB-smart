package com.tvo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class CompanyEntityPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "COMP_CODE")
	private String compCode;
    
	@Column(name = "MCN")
    private String mcn;
	
	@Column(name = "MP")
    private String mp;

}
