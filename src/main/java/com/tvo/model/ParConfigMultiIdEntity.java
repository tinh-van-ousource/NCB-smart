package com.tvo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PAR_CONFIG")
@NoArgsConstructor
@Setter
@Getter
@IdClass(ParConfigCompositeKey.class)
public class ParConfigMultiIdEntity implements Serializable { // Su dung cho param co nhieu hon 1 id

    private static final long serialVersionUID = 7003030148868469074L;

    @Id
    @Column(name = "PARAM")
    private String param;

    @Id
    @Column(name = "CODE")
    private String code;

    @Column(name = "VALUE")
    private String value;

    @Column(name = "NOTE")
    private String note;

}
