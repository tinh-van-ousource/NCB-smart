package com.tvo.service;

import com.tvo.controllerDto.RoleCreateReqDto;
import com.tvo.controllerDto.RoleSearchReqDto;
import com.tvo.controllerDto.RoleUpdateReqDto;
import com.tvo.dto.ContentResDto;
import com.tvo.dto.RoleResDto;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    ContentResDto search(RoleSearchReqDto roleSearchReqDto);

    RoleResDto updateRole(RoleUpdateReqDto roleReqDto);

    RoleResDto createRole(RoleCreateReqDto roleReqDto);

    RoleResDto getRoleById(Long id);
    
    public Boolean delete(String roleName);
}