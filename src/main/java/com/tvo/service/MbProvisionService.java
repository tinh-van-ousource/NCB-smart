package com.tvo.service;

import com.tvo.controllerDto.SearchMbProvisionModel;
import com.tvo.dto.MbProvisionResDto;
import com.tvo.dto.ParCardPictureDto;
import com.tvo.request.CreateMbProvisionRequest;
import com.tvo.request.UpdateMbProvisionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Thanglt
 * @version 1.0
 * @date Aug 8, 2019
 */
public interface MbProvisionService {

    public MbProvisionResDto findById(Long id);

    public Page<MbProvisionResDto> search(SearchMbProvisionModel searchModel, Pageable pageable);

    public MbProvisionResDto update(UpdateMbProvisionRequest request);

    public MbProvisionResDto create(CreateMbProvisionRequest request);

    public Boolean delete(Long id);
}
