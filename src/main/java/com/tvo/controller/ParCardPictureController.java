package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.dto.ParCardPictureDto;
import com.tvo.request.CreateParCardPictureRequest;
import com.tvo.request.ParCardPictureSearchDto;
import com.tvo.request.UpdateParCardPictureRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.ParCardPictureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/parcard-picture")
public class ParCardPictureController {
	@Autowired
	ParCardPictureServiceImpl parCardPictureServiceImpl;
	@PostMapping(value="/create")
	public ResponeData<ParCardPictureDto> create(@RequestBody CreateParCardPictureRequest request) {
		ParCardPictureDto dto = parCardPictureServiceImpl.create(request);
		if(dto == null) {
			return new ResponeData<ParCardPictureDto>(AppConstant.CITY_CREATE_DUPLICATE_ERROR_CODE, AppConstant.CITY_CREATE_DUPLICATE_ERROR_MESSAGE, null);
		}
		return new ResponeData<ParCardPictureDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	}
	@GetMapping(value = "/search")
	public ResponeData<Page<ParCardPictureDto>> search(@ModelAttribute ParCardPictureSearchDto searchPicture, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable){
		Page<ParCardPictureDto> CityDtos = parCardPictureServiceImpl.search(searchPicture, pageable);
		return new ResponeData<Page<ParCardPictureDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, CityDtos);
	}
	@PutMapping(value = "update")
	public ResponeData<ParCardPictureDto> update(@RequestBody UpdateParCardPictureRequest request) {
		ParCardPictureDto cityDto = parCardPictureServiceImpl.update(request);
		if (cityDto == null) {
			return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, cityDto);
	}
	@GetMapping(value = "/detail")

	    public ResponeData<ParCardPictureDto> detail(@RequestParam String fileName) {
		ParCardPictureDto dto = parCardPictureServiceImpl.detail(fileName);
	        if (dto == null) {
	            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
	        }
	        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	    }

	@DeleteMapping(value = "delete")
	public ResponeData<Boolean> delete(@RequestParam String fileName) {
		boolean deleteFlag = parCardPictureServiceImpl.delete(fileName);
        if (deleteFlag) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
    }
	 @GetMapping(value = "list-linkUrl")
	    public ResponeData<List<String>> getAllLinkUrl() {
	        List<String> serviceList = parCardPictureServiceImpl.getListLinkUrlMbApp();
	        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, serviceList);
	    }
}
