package com.tvo.validator;

import com.tvo.enums.StatusActivate;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StatusActivateValidator implements
        ConstraintValidator<StatusActivateConstraint, String> {

    @Override
    public void initialize(StatusActivateConstraint statusActivateConstraint) {
    }

    @Override
    public boolean isValid(String status, ConstraintValidatorContext cxt) {
        StatusActivate[] allStatuses = StatusActivate.values();

        if (status == null || status.trim().equals(StringUtils.EMPTY)) {
            return true;
        }

        for (StatusActivate statusActivate : allStatuses) {
            if (statusActivate.getStatus().equals(status)) {
                return true;
            }
        }

        return false;
    }

}