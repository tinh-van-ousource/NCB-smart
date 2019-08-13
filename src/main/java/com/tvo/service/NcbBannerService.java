package com.tvo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.SearchNcbBannerModel;
import com.tvo.controllerDto.UpdateNcbBannerRequest;
import com.tvo.dto.NcbBannerDto;
import com.tvo.request.CreateNcbBannerRequest;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 13, 2019
 */
public interface NcbBannerService {
	public List<NcbBannerDto> findAll();

	public NcbBannerDto findById(Long id);

	public Page<NcbBannerDto> searchNcbBanner(SearchNcbBannerModel searchModel, Pageable pageable);

	public NcbBannerDto create(CreateNcbBannerRequest request);
	
	public NcbBannerDto update(UpdateNcbBannerRequest request);

	public Boolean delete(Long id);
}
