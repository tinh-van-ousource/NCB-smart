/**
 * 
 */
package com.tvo.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.tvo.common.AppConstant;
import com.tvo.dao.AppRoleDAO;
import com.tvo.dao.AppUserDAO;
import com.tvo.model.Role;
import com.tvo.model.User;


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
    
//    @Autowired
//    private BranchDao branchRepository;
//    
//    @Autowired
//    private TransactionCenterDao transactionCenterDao;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
//         Roles
        if (roleRepository.findByRoleName("ROLE_ADMIN") == null) {
        	Role role = new Role();
        	role.setRoleName("ROLE_ADMIN");
        	role.setDescription("ad");
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
        if (userRepository.findByUserName("sondaik") == null) {
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setUserName("sondaik");
            admin.setFullName("Ngo Van Son");
            admin.setPassword(passwordEncoder.encode("123456"));
//            roles.add(roleRepository.findByRoleName("ROLE_ADMIN"));
            admin.setRole(roleRepository.findByRoleName("ROLE_ADMIN"));
            admin.setStatus(AppConstant.Status.ACTIVE.getValue());
            userRepository.save(admin);
        }

        // USER account
        if (userRepository.findByUserName("user1") == null) {
            User user = new User();
            user.setEmail("USER@gmail.com");
            user.setUserName("user1");
            user.setFullName("user 111");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRole(roleRepository.findByRoleName("ROLE_USER"));
            user.setStatus(AppConstant.Status.ACTIVE.getValue());
            userRepository.save(user);
        }
        
        if (userRepository.findByUserName("sonvipDaik") == null) {
            User user = new User();
            user.setEmail("son@gmail.com");
            user.setUserName("sonvipDaik");
            user.setFullName("user 22");
            user.setPassword(passwordEncoder.encode("Son123"));
            user.setRole(roleRepository.findByRoleName("ROLE_USER"));
            user.setStatus(AppConstant.Status.ACTIVE.getValue());
            userRepository.save(user);
        }
//        if(branchRepository.findByBranchName("Hà Nội") == null) {
//        	  Branch branch = new Branch();
//      		branch.setBranchName("Hà Nội");
//      		branchRepository.save(branch);
//        }
//        
//        if(transactionCenterDao.findByTransactionName("phòng giao dịch số 1")== null) {
//        	TransactionCenter transactionCenter = new TransactionCenter();
//        	transactionCenter.setTransactionName("phòng giao dịch số 1");
//        	transactionCenter.setBranch(branchRepository.findByBranchName("Hà Nội"));
//        	transactionCenterDao.save(transactionCenter);
//        }
      
    }

}