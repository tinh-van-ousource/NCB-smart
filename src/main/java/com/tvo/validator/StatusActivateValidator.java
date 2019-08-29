package com.tvo.validator;

import com.tvo.enums.StatusActivate;

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

        if (status == null) {
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