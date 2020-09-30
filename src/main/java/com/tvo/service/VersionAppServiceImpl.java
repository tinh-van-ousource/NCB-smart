package com.tvo.service;

import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.config.Flag;
import com.tvo.dao.ConfigMbAppDAO;
import com.tvo.dto.ConfigMbAppDto;
import com.tvo.model.ConfigMbApp;
import com.tvo.request.CreateVersionAppRequest;
import com.tvo.request.UpdateVersionAppRequest;
import com.tvo.response.ResponeData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.util.List;

/**
 * @author Nguyen Hoang Long on 9/28/2020
 * @created 9/28/2020
 * @project NCB-smart
 */
@Service
public class VersionAppServiceImpl implements VersionAppService{

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    InetAddress ip;

    String hostname;

    @Autowired
    private ConfigMbAppDAO configMbAppDAO;

    @Override
    public ResponeData<List<ConfigMbAppDto>> list() throws Exception {
        List<ConfigMbApp> configMbAppList = configMbAppDAO.getListVersionApp();
        if (configMbAppList == null) {
            logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, null);
        }
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Lấy danh sách Version App");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE,AppConstant.SYSTEM_SUCCESS_MESSAGE,ModelMapperUtils.mapAll(configMbAppList,ConfigMbAppDto.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<ConfigMbAppDto> created(CreateVersionAppRequest createVersionAppRequest) throws Exception {
        ConfigMbApp configMbApp = configMbAppDAO.findVersionAppByCode(createVersionAppRequest.getCode());
        if (configMbApp != null) {
            logger.warn(AppConstant.CITY_CREATE_DUPLICATE_ERROR_MESSAGE + " " + configMbApp.getCode());
            return new ResponeData<>(AppConstant.CITY_CREATE_DUPLICATE_ERROR_CODE,
                    AppConstant.CITY_CREATE_DUPLICATE_ERROR_MESSAGE + " Code : " + configMbApp.getCode(), null);
        }
        configMbApp = setCreate(createVersionAppRequest);
        ConfigMbApp save = configMbAppDAO.save(configMbApp);
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Tạo mới Version App");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE,AppConstant.SYSTEM_SUCCESS_MESSAGE,ModelMapperUtils.map(save,ConfigMbAppDto.class));
    }

    private static ConfigMbApp setCreate(CreateVersionAppRequest createVersionAppRequest){
        ConfigMbApp configMbApp = new ConfigMbApp();
        configMbApp.setCode(StringUtils.isEmpty(createVersionAppRequest.getCode()) ? null : createVersionAppRequest.getCode());
        configMbApp.setValue(StringUtils.isEmpty(createVersionAppRequest.getValue()) ? null : createVersionAppRequest.getValue());
        configMbApp.setName(StringUtils.isEmpty(createVersionAppRequest.getName()) ? null : createVersionAppRequest.getName());
        configMbApp.setType(StringUtils.isEmpty(createVersionAppRequest.getType()) ? null : createVersionAppRequest.getType());
        configMbApp.setSort(StringUtils.isEmpty(createVersionAppRequest.getSort()) ? null : createVersionAppRequest.getSort());
        configMbApp.setDescription(StringUtils.isEmpty(createVersionAppRequest.getDescription()) ? null : createVersionAppRequest.getDescription());
        return configMbApp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<ConfigMbAppDto> updated(String code,UpdateVersionAppRequest updateVersionAppRequest) throws Exception {
        ConfigMbApp configMbApp = configMbAppDAO.findVersionAppByCode(code);
        if (configMbApp == null) {
            logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, null);
        }
        configMbApp = setUpdate(configMbApp,updateVersionAppRequest);
        ConfigMbApp update = configMbAppDAO.save(configMbApp);
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Cập nhật thông tin Version App");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE,AppConstant.SYSTEM_SUCCESS_MESSAGE,ModelMapperUtils.map(update,ConfigMbAppDto.class));
    }

    private static ConfigMbApp setUpdate(ConfigMbApp configMbApp,UpdateVersionAppRequest updateVersionAppRequest){
        if(!StringUtils.isEmpty(updateVersionAppRequest.getCode())){
            configMbApp.setCode(updateVersionAppRequest.getCode());
        }
        if(!StringUtils.isEmpty(updateVersionAppRequest.getValue())){
            configMbApp.setValue(updateVersionAppRequest.getValue());
        }
        if(!StringUtils.isEmpty(updateVersionAppRequest.getName())){
            configMbApp.setName(updateVersionAppRequest.getName());
        }
        if(!StringUtils.isEmpty(updateVersionAppRequest.getType())){
            configMbApp.setType(updateVersionAppRequest.getType());
        }
        if(!StringUtils.isEmpty(updateVersionAppRequest.getSort())){
            configMbApp.setSort(updateVersionAppRequest.getSort());
        }
        if(!StringUtils.isEmpty(updateVersionAppRequest.getDescription())){
            configMbApp.setDescription(updateVersionAppRequest.getDescription());
        }
        return configMbApp;
    }

    @Override
    public ResponeData<ConfigMbAppDto> details(String code) throws Exception {
        ConfigMbApp configMbApp = configMbAppDAO.findVersionAppByCode(code);
        if (configMbApp == null) {
            logger.warn(AppConstant.FILE_NOT_FOUND_MESSAGE);
            return new ResponeData<>(AppConstant.FILE_NOT_FOUND_CODE, AppConstant.FILE_NOT_FOUND_MESSAGE, null);
        }
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                "\n Account :" + Flag.userFlag.getUserName() +
                "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                " \n Địa chỉ IP đăng nhập : " + ip +
                " \n Hostname : " + hostname +
                " \n Tìm kiếm thông tin Version App");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE,AppConstant.SYSTEM_SUCCESS_MESSAGE,ModelMapperUtils.map(configMbApp,ConfigMbAppDto.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponeData<Boolean> deleted(String code) throws Exception {
        ConfigMbApp configMbApp = configMbAppDAO.findVersionAppByCode(code);
        System.out.println(configMbApp.getCode()+"hhh"+configMbApp.getValue());
        if (configMbApp != null) {
            configMbAppDAO.delete(configMbApp);
                ip = InetAddress.getLocalHost();
                hostname = ip.getHostName();
                logger.info(" \n Người dùng:" + Flag.userFlag.getFullName() +
                        "\n Account :" + Flag.userFlag.getUserName() +
                        "\n Role :" + Flag.userFlag.getRole().getRoleName() +
                        " \n Địa chỉ IP đăng nhập : " + ip +
                        " \n Hostname : " + hostname +
                        " \n Xóa Version App");
                return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
        }else {
            logger.warn(AppConstant.SYSTEM_ERROR_CODE);
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
        }
    }
}
