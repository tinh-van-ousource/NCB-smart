package com.tvo.controllerDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleUpdateReqDto {
    private String roleName;

    private String description;

    private String briefDescription;
    
    private String status;
}
