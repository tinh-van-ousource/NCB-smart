package com.tvo.validator;

import com.tvo.enums.ServiceRegisterStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ServiceRegisterStatusValidator implements
        ConstraintValidator<ServiceRegisterStatusConstraint, Integer> {

    @Override
    public void initialize(ServiceRegisterStatusConstraint serviceRegisterServiceConstraint) {
    }

    @Override
    public boolean isValid(Integer status, ConstraintValidatorContext cxt) {
        ServiceRegisterStatus[] allTypes = ServiceRegisterStatus.values();

        for (ServiceRegisterStatus type : allTypes) {
            if (type.getType().equals(status)) {
                return true;
            }
        }

        return false;
    }

}