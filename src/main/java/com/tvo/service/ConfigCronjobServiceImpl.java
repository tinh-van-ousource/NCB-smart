package com.tvo.service;

import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.ConfigCronjobRqDto;
import com.tvo.dao.ConfigCronjobDAO;
import com.tvo.model.ConfigCronjob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConfigCronjobServiceImpl implements ConfigCronjobService {

    private static final String ID_TABLE_CONFIG = "config_mbapp";

    @Autowired
    private ConfigCronjobDAO configCronjobDAO;

    @Override
    public ConfigCronjob saveOrUpdate(ConfigCronjobRqDto configCronjobRqDto) {
        ConfigCronjob cronjobRs;
        ConfigCronjob cronjobTemp = ModelMapperUtils.map(configCronjobRqDto, ConfigCronjob.class);
        List<ConfigCronjob> cronjobDB = configCronjobDAO.findAll();
        if (cronjobDB.isEmpty()) {
            cronjobTemp.setJobId(ID_TABLE_CONFIG);
            cronjobTemp.setCreatedDate(DateTimeUtil.getNow());
        } else {
            cronjobTemp.setJobId(cronjobDB.get(0).getJobId());
            cronjobTemp.setCreatedDate(cronjobDB.get(0).getCreatedDate());
            cronjobTemp.setUpdatedDate(DateTimeUtil.getNow());
        }
        cronjobRs = configCronjobDAO.save(cronjobTemp);
        return cronjobRs;
    }
}
