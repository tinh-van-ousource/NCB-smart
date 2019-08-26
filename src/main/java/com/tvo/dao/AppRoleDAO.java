/**
 * 
 */
package com.tvo.dao;

import com.tvo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ace
 *
 */
@Repository
public interface AppRoleDAO extends JpaRepository<Role, Long> {
	Role findByRoleName(String roleName);
}
