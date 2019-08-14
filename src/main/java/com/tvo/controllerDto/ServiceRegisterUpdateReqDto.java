package com.tvo.controllerDto;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author NgocDC
 */
@Setter
@Getter
public class ServiceRegisterUpdateReqDto {

    private String compCode; // ma chi nhanh - phong giao dich

    private String compName; // ten chi nhanh - phong giao dich

    private String status; // trang thai

    private String comment; // comment

    private String userId; // nguoi updates

}
