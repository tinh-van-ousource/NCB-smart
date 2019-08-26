/**
 * 
 */
package com.tvo.config;

import com.tvo.common.AppConstant;
import com.tvo.dao.AppRoleDAO;
import com.tvo.dao.AppUserDAO;
import com.tvo.model.Role;
import com.tvo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


/**
 * @author Ace
 *
 */
@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private AppUserDAO userRepository;

    @Autowired
    private AppRoleDAO roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
//         Roles
        if (roleRepository.findByRoleName("ROLE_ADMIN") == null) {
        	Role role = new Role();
        	role.setRoleName("ROLE_ADMIN");
        	role.setDescription("admin");
        	role.setStatus(AppConstant.Status.ACTIVE.getValue());
            roleRepository.save(role);
        }

        if (roleRepository.findByRoleName("ROLE_USER") == null) {
            Role role = new Role();
        	role.setRoleName("ROLE_USER");
        	role.setDescription("user");
        	role.setStatus(AppConstant.Status.ACTIVE.getValue());
            roleRepository.save(role);
        }

        // Admin account
        if (userRepository.findByUserName("admin") == null) {
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setUserName("admin");
            admin.setFullName("Toi la admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRole(roleRepository.findByRoleName("ROLE_ADMIN"));
            admin.setStatus(AppConstant.Status.ACTIVE.getValue());
            userRepository.save(admin);
        }

        // USER account
        if (userRepository.findByUserName("user") == null) {
            User user = new User();
            user.setEmail("USER@gmail.com");
            user.setUserName("user");
            user.setFullName("Toi la user");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRole(roleRepository.findByRoleName("ROLE_USER"));
            user.setStatus(AppConstant.Status.ACTIVE.getValue());
            userRepository.save(user);
        }

    }

}