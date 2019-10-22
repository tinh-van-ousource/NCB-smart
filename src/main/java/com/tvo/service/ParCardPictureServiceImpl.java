package com.tvo.service;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tvo.controllerDto.ParCardSearchReqDto;
import com.tvo.dao.ParamManagerDao;
import com.tvo.dto.ParCardPictureDto;
import com.tvo.dto.ParCardProductResDto;
import com.tvo.request.CreateParCardPictureRequest;
import com.tvo.request.ParCardPictureSearchDto;
import com.tvo.request.ParCardProductCreateReqDto;
import com.tvo.request.UpdateParCardPictureRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParCardPictureServiceImpl implements ParCardPictureService{@Override
	public Page<ParCardPictureDto> search(ParCardPictureSearchDto searchModel, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParCardPictureDto createCity(CreateParCardPictureRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParCardPictureDto update(UpdateParCardPictureRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delete(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParCardPictureDto detail(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}


}
