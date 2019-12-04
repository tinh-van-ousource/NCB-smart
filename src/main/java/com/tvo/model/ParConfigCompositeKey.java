package com.tvo.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ParConfigCompositeKey implements Serializable {

    private static final long serialVersionUID = 1620142205469262829L;
    private String param;
    private String code;

}
