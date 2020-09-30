package com.tvo.service;

import com.tvo.dto.ConfigMbAppDto;
import com.tvo.request.CreateVersionAppRequest;
import com.tvo.request.UpdateVersionAppRequest;
import com.tvo.response.ResponeData;

import java.util.List;

/**
 * @author Nguyen Hoang Long on 9/28/2020
 * @created 9/28/2020
 * @project NCB-smart
 */

public interface VersionAppService {
    /**
     * Search Version app
     *
     * @return ResponeData<List<ConfigMbAppDto>>
     * @throws Exception
     */
    ResponeData<List<ConfigMbAppDto>> list()throws Exception;

    /**
     * Created Version app
     *
     * @return ResponeData<ConfigMbAppDto>
     * @throws Exception
     */
    ResponeData<ConfigMbAppDto> created(CreateVersionAppRequest createVersionAppRequest)throws Exception;

    /**
     * Updated Version app
     *
     * @return ResponeData<ConfigMbAppDto>
     * @throws Exception
     */
    ResponeData<ConfigMbAppDto> updated(String code, UpdateVersionAppRequest updateVersionAppRequest)throws Exception;

    /**
     * Details Version app
     *
     * @param code
     * @return ResponeData<ConfigMbAppDto>
     * @throws Exception
     */
    ResponeData<ConfigMbAppDto> details(String code)throws Exception;

    /**
     * Delete Version app
     *
     * @param id id
     * @return Boolean
     * @throws Exception
     */
    ResponeData<Boolean> deleted(String code)throws Exception;
}
