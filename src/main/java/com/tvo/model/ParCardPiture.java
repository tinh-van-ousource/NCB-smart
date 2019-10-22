package com.tvo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PAR_CARD_PICTURE")
@NoArgsConstructor
@Setter
@Getter
@IdClass(ParConfigCompositeKey.class)
public class ParCardPiture implements Serializable{
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_PICTURE")
    @SequenceGenerator(sequenceName = "AI_PAR_CARD_SQ", allocationSize = 1, name = "ID_PICTURE")
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "FILE_NAME")
	private String fileName;

	@Column(name = "LINK_ULR")
	private String linkUrl;

	@Column(name = "NOTE")
	private String note;

	@Column(name = "STATUS")
	private String status;
    
}
