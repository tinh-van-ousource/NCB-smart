package com.tvo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.dto.ParCardPictureDto;
import com.tvo.request.CreateParCardPictureRequest;
import com.tvo.request.ParCardPictureSearchDto;
import com.tvo.request.UpdateParCardPictureRequest;

public interface ParCardPictureService {
	Page<ParCardPictureDto> search(ParCardPictureSearchDto searchModel, Pageable pageable);
    public ParCardPictureDto createCity(CreateParCardPictureRequest request) ;
	public ParCardPictureDto update(UpdateParCardPictureRequest request);
	
	public Boolean delete(String fileName);
	
	public ParCardPictureDto detail(String fileName);
}
