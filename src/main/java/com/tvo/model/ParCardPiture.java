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
public class ParCardPiture implements Serializable{

    @Id
    @Column(name = "FILE_NAME")
	private String fileName;

	@Column(name = "LINK_ULR")
	private String linkUrl;

	@Column(name = "NOTE")
	private String note;

	@Column(name = "STATUS")
	private String status;
    
}
