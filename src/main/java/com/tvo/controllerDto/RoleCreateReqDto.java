package com.tvo.controllerDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleCreateReqDto {
    private String roleName;

    private String description;

    private String briefDescription;
    
    private String status;
}
