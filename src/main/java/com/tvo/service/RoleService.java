/**
 * 
 */
package com.tvo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tvo.dto.RoleDto;
import com.tvo.model.Role;

/**
 * @author Ace
 *
 */
@Service
public interface RoleService {
	public List<RoleDto> findAllRole();
	public Role save(RoleDto roleDto);
}




 	