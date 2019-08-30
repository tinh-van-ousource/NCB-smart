package com.tvo.config;

import com.tvo.dao.UserRepo;
import com.tvo.enums.StatusActivate;
import com.tvo.model.User;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final UserRepo userRepo;

    public AuthenticationFailureListener(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String username = event.getAuthentication().getPrincipal().toString();
//        String password = event.getAuthentication().getCredentials().toString();

        User user = userRepo.findByUserName(username);
        user.setCountLoginFail(user.getCountLoginFail() + 1);
        if (user.getCountLoginFail() >= 5) {
            user.setStatus(StatusActivate.STATUS_DEACTIVATED.getStatus());
        }
        userRepo.save(user);
    }
}