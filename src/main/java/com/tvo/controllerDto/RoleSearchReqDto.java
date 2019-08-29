package com.tvo.controllerDto;

import com.tvo.validator.StatusActivateConstraint;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
public class RoleSearchReqDto {
    private String roleName;

    @StatusActivateConstraint
    private String status;

    @Positive
    @Min(1)
    @NotNull
    private Integer page;

    @Positive
    @Min(1)
    @NotNull
    private Integer size;
}
