/**
 * 
 */
package com.tvo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tvo.model.Role;

/**
 * @author Ace
 *
 */
@Repository
public interface AppRoleDAO extends JpaRepository<Role, Long> {
	Role findByRoleName(String roleName);
}
