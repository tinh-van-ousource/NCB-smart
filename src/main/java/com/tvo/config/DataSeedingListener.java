/**
 * 
 */
package com.tvo.config;

import com.tvo.dao.RoleRepo;
import com.tvo.dao.UserRepo;
import com.tvo.enums.StatusActivate;
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
    private UserRepo userRepository;

    @Autowired
    private RoleRepo roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
//         Roles
        if (roleRepository.findByRoleName("ROLE_ADMIN") == null) {
        	Role role = new Role();
        	role.setRoleName("ROLE_ADMIN");
        	role.setDescription("admin");
        	role.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());
            roleRepository.save(role);
        }

        if (roleRepository.findByRoleName("ROLE_USER") == null) {
            Role role = new Role();
        	role.setRoleName("ROLE_USER");
        	role.setDescription("user");
        	role.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());
            roleRepository.save(role);
        }

        // Admin account
        if (userRepository.findByUserName("admin") == null) {
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setUserName("admin");
            admin.setFullName("Toi la admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setPhone("0901234567");
            admin.setBranchCode("VN0010001");
            admin.setTransactionCode("VN0010001");
            admin.setRole(roleRepository.findByRoleName("ROLE_ADMIN"));
            admin.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());
            admin.setLoginCount(0L);
            userRepository.save(admin);
        }

        // USER account
        if (userRepository.findByUserName("user") == null) {
            User user = new User();
            user.setEmail("USER@gmail.com");
            user.setUserName("user");
            user.setFullName("Toi la user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setPhone("0901234567");
            user.setBranchCode("VN0010001");
            user.setTransactionCode("VN0010001");
            user.setRole(roleRepository.findByRoleName("ROLE_USER"));
            user.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());
            user.setLoginCount(0L);
            userRepository.save(user);
        }

    }

}