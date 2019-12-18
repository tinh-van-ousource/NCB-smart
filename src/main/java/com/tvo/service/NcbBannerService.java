package com.tvo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.SearchNcbBannerModel;
import com.tvo.controllerDto.UpdateNcbBannerRequest;
import com.tvo.dto.ConfigMbAppRsDto;
import com.tvo.dto.NcbBannerDto;
import com.tvo.model.NcbBanner;
import com.tvo.request.CreateNcbBannerRequest;

/**
 * @author Thanglt
 * @version 1.0
 * @date Aug 13, 2019
 */
public interface NcbBannerService {
    public NcbBannerDto findById(Long id);

    public Page<NcbBannerDto> searchNcbBanner(SearchNcbBannerModel searchModel, Pageable pageable);

    public NcbBanner create(CreateNcbBannerRequest request);

    public NcbBannerDto update(UpdateNcbBannerRequest request);

    public Boolean delete(Long id);
    
    List<ConfigMbAppRsDto> getListForwardingScreen();
}
