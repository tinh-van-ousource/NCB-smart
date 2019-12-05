package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.RoleCreateReqDto;
import com.tvo.controllerDto.RoleSearchReqDto;
import com.tvo.controllerDto.RoleUpdateReqDto;
import com.tvo.dao.RoleRepo;
import com.tvo.dto.ContentResDto;
import com.tvo.dto.RoleResDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.Notify;
import com.tvo.model.ProviderEntity;
import com.tvo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepo roleRepo;

    @Override
    public ContentResDto search(RoleSearchReqDto roleSearchReqDto) {
        List<Role> roles = roleRepo.search(roleSearchReqDto);
        Long roleCount = roleRepo.searchCount(roleSearchReqDto);

        ContentResDto contentResDto = new ContentResDto(Collections.EMPTY_LIST, 0L);

        if (!roles.isEmpty()) {
            List<RoleResDto> roleResDtoList = ModelMapperUtils.mapAll(roles, RoleResDto.class);
            contentResDto.setContent(roleResDtoList);
            contentResDto.setTotal(roleCount);
            return contentResDto;
        }

        return contentResDto;
    }

    @Override
    public RoleResDto getRoleById(Long id) {
        Optional<Role> role = roleRepo.findById(id);
        return role.map(value -> ModelMapperUtils.map(value, RoleResDto.class)).orElse(null);
    }

    @Override
    public RoleResDto updateRole(RoleUpdateReqDto roleReqDto) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        Role role = roleRepo.findByRoleName(roleReqDto.getRoleName());
        if (role != null) {
            role.setStatus(roleReqDto.getStatus());
            role.setDescription(roleReqDto.getDescription());
            role.setUpdatedBy(currentUserName);
            return ModelMapperUtils.map(roleRepo.save(role), RoleResDto.class);
        }
        return null;
    }

    @Override
    public RoleResDto createRole(RoleCreateReqDto roleReqDto) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        Role oldRole = roleRepo.findByRoleName(roleReqDto.getRoleName());
        if (oldRole != null) {
            return null;
        }

        Role role = ModelMapperUtils.map(roleReqDto, Role.class);
        role.setUpdatedBy(currentUserName);
        if (roleReqDto.getStatus() == null) {
            role.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());
        }

        return ModelMapperUtils.map(roleRepo.save(role), RoleResDto.class);
    }

	@Override
	public Boolean delete(String roleName) {
		Role notify = roleRepo.findByRoleName(roleName);
		if (notify != null) {
			roleRepo.delete(notify);
			return true;
		}
		return false;
	}

	

}
