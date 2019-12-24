package com.tvo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PAR_CONFIG")
@NoArgsConstructor
@Getter
@Setter
public class ParConfigMultiIdEntity implements Serializable {
	private static final long serialVersionUID = 6985682121897152087L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AI_PAR_CONFIG_SQ_GENERATOR")
    @SequenceGenerator(sequenceName = "AI_PAR_CONFIG_SQ", allocationSize = 1, name = "AI_PAR_CONFIG_SQ_GENERATOR")
	private Long id;

	@Column(name = "PARAM")
	private String param;
	
	@Column(name = "CODE")
    private String code;
	
	@Column(name = "VALUE")
	private String value;

	@Column(name = "NOTE")
	private String note;

}
