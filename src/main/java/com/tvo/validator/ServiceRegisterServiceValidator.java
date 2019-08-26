package com.tvo.validator;

import com.tvo.enums.ServiceRegisterServiceType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ServiceRegisterServiceValidator implements
        ConstraintValidator<ServiceRegisterServiceConstraint, Integer> {

    @Override
    public void initialize(ServiceRegisterServiceConstraint serviceRegisterServiceConstraint) {
    }

    @Override
    public boolean isValid(Integer serviceType, ConstraintValidatorContext cxt) {
        ServiceRegisterServiceType[] allTypes = ServiceRegisterServiceType.values();

        if (serviceType == null) {
            return true;
        }

        for (ServiceRegisterServiceType type : allTypes) {
            if (type.getType().equals(serviceType)) {
                return true;
            }
        }

        return false;
    }

}