/**
 *
 */
package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.RoleCreateReqDto;
import com.tvo.controllerDto.RoleUpdateReqDto;
import com.tvo.dao.AppRoleDAO;
import com.tvo.dto.RoleResDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ace
 *
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    AppRoleDAO appRoleDAO;

    @Override
    public List<RoleResDto> findAllRole() {
        return ModelMapperUtils.mapAll(appRoleDAO.findAll(), RoleResDto.class);
    }

    @Override
    public RoleResDto updateRole(RoleUpdateReqDto roleReqDto) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        Role role = appRoleDAO.findByRoleName(roleReqDto.getRoleName());
        if (role != null) {
            role.setStatus(roleReqDto.getStatus());
            role.setDescription(roleReqDto.getDescription());
            role.setUpdatedBy(currentUserName);
            return ModelMapperUtils.map(appRoleDAO.save(role), RoleResDto.class);
        }
        return null;
    }

    @Override
    public RoleResDto createRole(RoleCreateReqDto roleReqDto) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        Role oldRole = appRoleDAO.findByRoleName(roleReqDto.getRoleName());
        if (oldRole != null) {
            return null;
        }

        Role role = ModelMapperUtils.map(roleReqDto, Role.class);
        role.setUpdatedBy(currentUserName);
        role.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());
        return ModelMapperUtils.map(appRoleDAO.save(role), RoleResDto.class);
    }

}
