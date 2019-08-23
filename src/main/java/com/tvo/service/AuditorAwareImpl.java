/**
 * 
 */
package com.tvo.service;

import com.tvo.dao.AppUserDAO;
import com.tvo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Ace
 *
 */
public class AuditorAwareImpl implements AuditorAware<User>{

	@Autowired
	AppUserDAO appUserDAO;

	@Override
    @Transactional
	public Optional<User> getCurrentAuditor() {
        User auditor = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                auditor = (User) principal;
            }
        }
        return Optional.ofNullable(auditor);
	}

}
