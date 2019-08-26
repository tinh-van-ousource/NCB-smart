/**
 * 
 */
package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.dao.AppRoleDAO;
import com.tvo.dto.RoleDto;
import com.tvo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ace
 *
 */
@Service
@Transactional
public class RoleServiceIplm implements RoleService{

	@Autowired
	AppRoleDAO appRoleDAO;
	
	@Override
	public List<RoleDto> findAllRole() {
		return ModelMapperUtils.mapAll(appRoleDAO.findAll(), RoleDto.class);
	}

	@Override
	public Role save(RoleDto roleDto) {
		Role role = ModelMapperUtils.map(roleDto, Role.class);
		return appRoleDAO.save(role);
	}

}
