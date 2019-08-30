package com.tvo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResDto {

    private Long roleId;

    private String roleName;

    private String description;

    private String status;

    private String updatedBy;
}
