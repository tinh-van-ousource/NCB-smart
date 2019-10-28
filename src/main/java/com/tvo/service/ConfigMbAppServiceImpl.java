package com.tvo.service;

import com.tvo.dao.ConfigMbAppDAO;
import com.tvo.dto.ConfigMbAppRsDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ConfigMbAppServiceImpl implements ConfigMbAppService {

    @Autowired
    private ConfigMbAppDAO configMbAppDAO;

    @Override
    public List<ConfigMbAppRsDto> findByTypeAndAndCode(String type, String code) {
        List<ConfigMbAppRsDto> configMbAppRsDtos = new ArrayList<>();
        List<Object> listObj;
        if (StringUtils.isNotBlank(type)) {
            listObj = configMbAppDAO.findByTypeAndAndCode(type, code);
        } else {
            listObj = configMbAppDAO.findByCode(code);
        }
        for (Object depart : listObj) {
            Object[] departs = (Object[]) depart;
            configMbAppRsDtos.add(
                    new ConfigMbAppRsDto(departs[0].toString(), departs[1].toString()));
        }
        return configMbAppRsDtos;
    }

    @Override
    public List<String> getTypeByCode(String code) {
        return configMbAppDAO.getTypeByCode(code);
    }
}
