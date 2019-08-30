/**
 *
 */
package com.tvo.dao;

import com.tvo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long>, RoleRepoCustom {
    Role findByRoleName(String roleName);
}
