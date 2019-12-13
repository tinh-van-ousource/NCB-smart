package com.tvo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleResDto {

    private Long roleId;

    private String roleName;

    private String description;

    private String briefDescription;
    
    private String status;

    private String updatedBy;
    
}
