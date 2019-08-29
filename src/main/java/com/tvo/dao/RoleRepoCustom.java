package com.tvo.dao;

import com.tvo.controllerDto.RoleSearchReqDto;
import com.tvo.model.Role;

import java.util.List;

public interface RoleRepoCustom {
    List<Role> search(RoleSearchReqDto roleSearchReqDto);

    Long searchCount(RoleSearchReqDto roleSearchReqDto);
}
