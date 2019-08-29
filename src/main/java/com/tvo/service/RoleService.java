/**
 *
 */
package com.tvo.service;

import com.tvo.controllerDto.RoleCreateReqDto;
import com.tvo.controllerDto.RoleSearchReqDto;
import com.tvo.controllerDto.RoleUpdateReqDto;
import com.tvo.dto.RoleResDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ace
 *
 */
@Service
public interface RoleService {
    List<RoleResDto> search(RoleSearchReqDto roleSearchReqDto);

    RoleResDto updateRole(RoleUpdateReqDto roleReqDto);

    RoleResDto createRole(RoleCreateReqDto roleReqDto);
}




 	