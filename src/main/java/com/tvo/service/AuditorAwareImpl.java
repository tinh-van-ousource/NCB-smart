/**
 *
 */
package com.tvo.service;

import com.tvo.dao.AppUserDAO;
import com.tvo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * @author Ace
 *
 */
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Autowired
    AppUserDAO appUserDAO;

    @Override
    public Optional<Long> getCurrentAuditor() {
        try {
            User user = new User();
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                user = appUserDAO.findByUserName(username);
                return Optional.ofNullable(user.getUserId());
            } else {
                String username = principal.toString();
                user = appUserDAO.findByUserName(username);
                return Optional.ofNullable(user.getUserId());
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
