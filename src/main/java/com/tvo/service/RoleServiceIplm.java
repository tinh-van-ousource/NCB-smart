/**
 *
 */
package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.RoleUpdateReqDto;
import com.tvo.dao.AppRoleDAO;
import com.tvo.dto.RoleResDto;
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
public class RoleServiceIplm implements RoleService {

    @Autowired
    AppRoleDAO appRoleDAO;

    @Override
    public List<RoleResDto> findAllRole() {
        return ModelMapperUtils.mapAll(appRoleDAO.findAll(), RoleResDto.class);
    }

    @Override
    public RoleResDto updateRole(RoleUpdateReqDto roleReqDto) {
        Role role = appRoleDAO.findByRoleName(roleReqDto.getRoleName());
        if (role != null) {
            role.setStatus(roleReqDto.getStatus());
            role.setDescription(roleReqDto.getDescription());
            return ModelMapperUtils.map(appRoleDAO.save(role), RoleResDto.class);
        }
        return null;
    }

}
