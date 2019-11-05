package com.tvo.service;

import com.tvo.common.DateTimeUtil;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.ConfigCronjobRqDto;
import com.tvo.dao.ConfigCronjobDAO;
import com.tvo.dao.ConfigMbAppDAO;
import com.tvo.model.ConfigCronjob;
import com.tvo.model.ConfigMbApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConfigCronjobServiceImpl implements ConfigCronjobService {

    private static final String ID_TABLE_CONFIG = "config_mbapp";
    private static final String CONFIG_SORT_DEFAULT = "1";
    private static final String CONFIG_NAME_DEFAULT = "Schedule and job change value";
    private static final String CONFIG_CODE_SYNC_IN = "SYNC_IN";
    private static final String CONFIG_TYPE_FT = "FT";
    private static final String CONFIG_DESCRIPTION = "Schedule and job change value Y to N, or N to Y";

    @Autowired
    private ConfigCronjobDAO configCronjobDAO;

    @Autowired
    private ConfigMbAppDAO configMbAppDAO;

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

    @Override
    public ConfigMbApp saveOrUpdateConfigValue(String newValue) {
        ConfigMbApp configMbApp = configMbAppDAO.findConfigMbAppByCodeAndType(CONFIG_CODE_SYNC_IN, CONFIG_TYPE_FT);
        if (configMbApp == null) {
            configMbApp = new ConfigMbApp();
            configMbApp.setSort(CONFIG_SORT_DEFAULT);
            configMbApp.setName(CONFIG_NAME_DEFAULT);
            configMbApp.setCode(CONFIG_CODE_SYNC_IN);
            configMbApp.setType(CONFIG_TYPE_FT);
            configMbApp.setDescription(CONFIG_DESCRIPTION);
        }
        configMbApp.setValue(newValue);
        return configMbAppDAO.save(configMbApp);
    }
}
