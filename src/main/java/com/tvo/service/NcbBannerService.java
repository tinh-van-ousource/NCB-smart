package com.tvo.service;

import com.tvo.controllerDto.SearchNcbBannerModel;
import com.tvo.controllerDto.UpdateNcbBannerRequest;
import com.tvo.dto.NcbBannerDto;
import com.tvo.request.CreateNcbBannerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Thanglt
 * @version 1.0
 * @date Aug 13, 2019
 */
public interface NcbBannerService {
    public NcbBannerDto findById(Long id);

    public Page<NcbBannerDto> searchNcbBanner(SearchNcbBannerModel searchModel, Pageable pageable);

    public NcbBannerDto create(CreateNcbBannerRequest request);

    public NcbBannerDto update(UpdateNcbBannerRequest request);

    public Boolean delete(Long id);
}
