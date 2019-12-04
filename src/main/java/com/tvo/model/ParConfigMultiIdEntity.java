package com.tvo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PAR_CONFIG")
@NoArgsConstructor
@Getter
@Setter
public class ParConfigMultiIdEntity implements Serializable {
	private static final long serialVersionUID = 6985682121897152087L;
	@EmbeddedId
	private ParConfigCompositeKey key;

	@Column(name = "ID")
	private Long id;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "NOTE")
	private String note;

}
