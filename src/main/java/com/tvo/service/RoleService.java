/**
 * 
 */
package com.tvo.service;

import com.tvo.dto.RoleDto;
import com.tvo.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ace
 *
 */
@Service
public interface RoleService {
	public List<RoleDto> findAllRole();
	public Role save(RoleDto roleDto);
}




 	