package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.config.Flag;
import com.tvo.dto.ParCardPictureDto;
import com.tvo.request.CreateParCardPictureRequest;
import com.tvo.request.ParCardPictureSearchDto;
import com.tvo.request.UpdateParCardPictureRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.ParCardPictureServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@RestController
@RequestMapping(value = "/parcard-picture")
public class ParCardPictureController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	InetAddress ip;
    String hostname;
    
	@Autowired
	ParCardPictureServiceImpl parCardPictureServiceImpl;
	@PostMapping(value="/create")
	public ResponeData<ParCardPictureDto> create(@RequestBody CreateParCardPictureRequest request) {
		ParCardPictureDto dto = parCardPictureServiceImpl.create(request);
		if(dto == null) {
			return new ResponeData<ParCardPictureDto>(AppConstant.CITY_CREATE_DUPLICATE_ERROR_CODE, AppConstant.CITY_CREATE_DUPLICATE_ERROR_MESSAGE, null);
		}
        try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" +Flag.userFlag.getFullName().toString()+ 
        		"\n Account :"+Flag.userFlag.getUserName().toString()+
        		"\n Role :"+Flag.userFlag.getRole().getRoleName().toString()+
        		" \n Địa chỉ IP đăng nhập : " + ip+
        		" \n Hostname : " + hostname +
        		" \n Tạo mới Hình ảnh thẻ");
		return new ResponeData<ParCardPictureDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	}
	@GetMapping(value = "/search")
	public ResponeData<Page<ParCardPictureDto>> search(@ModelAttribute ParCardPictureSearchDto searchPicture, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable){
		Page<ParCardPictureDto> CityDtos = parCardPictureServiceImpl.search(searchPicture, pageable);
        try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" +Flag.userFlag.getFullName().toString()+ 
        		"\n Account :"+Flag.userFlag.getUserName().toString()+
        		"\n Role :"+Flag.userFlag.getRole().getRoleName().toString()+
        		" \n Địa chỉ IP đăng nhập : " + ip+
        		" \n Hostname : " + hostname +
        		" \n Tìm kiếm Hình ảnh thẻ");
		return new ResponeData<Page<ParCardPictureDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, CityDtos);
	}
	@PutMapping(value = "update")
	public ResponeData<ParCardPictureDto> update(@RequestBody UpdateParCardPictureRequest request) {
		ParCardPictureDto cityDto = parCardPictureServiceImpl.update(request);
		if (cityDto == null) {
			return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
        try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        hostname = ip.getHostName();
        logger.info(" \n Người dùng:" +Flag.userFlag.getFullName().toString()+ 
        		"\n Account :"+Flag.userFlag.getUserName().toString()+
        		"\n Role :"+Flag.userFlag.getRole().getRoleName().toString()+
        		" \n Địa chỉ IP đăng nhập : " + ip+
        		" \n Hostname : " + hostname +
        		" \n Cập nhật thông tin Hình ảnh thẻ");
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, cityDto);
	}
	@GetMapping(value = "/detail")

	    public ResponeData<ParCardPictureDto> detail(@RequestParam String fileName) {
		ParCardPictureDto dto = parCardPictureServiceImpl.detail(fileName);
	        if (dto == null) {
	            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
	        }
	        try {
				ip = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        hostname = ip.getHostName();
	        logger.info(" \n Người dùng:" +Flag.userFlag.getFullName().toString()+ 
	        		"\n Account :"+Flag.userFlag.getUserName().toString()+
	        		"\n Role :"+Flag.userFlag.getRole().getRoleName().toString()+
	        		" \n Địa chỉ IP đăng nhập : " + ip+
	        		" \n Hostname : " + hostname +
	        		" \n Chi tiết Hình ảnh thẻ");
	        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	    }

	@DeleteMapping(value = "delete")
	public ResponeData<Boolean> delete(@RequestParam String fileName) {
		boolean deleteFlag = parCardPictureServiceImpl.delete(fileName);
        if (deleteFlag) {
            try {
    			ip = InetAddress.getLocalHost();
    		} catch (UnknownHostException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            hostname = ip.getHostName();
            logger.info(" \n Người dùng:" +Flag.userFlag.getFullName().toString()+ 
            		"\n Account :"+Flag.userFlag.getUserName().toString()+
            		"\n Role :"+Flag.userFlag.getRole().getRoleName().toString()+
            		" \n Địa chỉ IP đăng nhập : " + ip+
            		" \n Hostname : " + hostname +
            		" \n Xóa Hình ảnh thẻ");
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.DELETED_SUCCESS_MESSAGE, true);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
    }
	 @GetMapping(value = "list-linkUrl")
	    public ResponeData<List<String>> getAllLinkUrl() {
	        List<String> serviceList = parCardPictureServiceImpl.getListLinkUrlMbApp();
	        try {
				ip = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        hostname = ip.getHostName();
	        logger.info(" \n Người dùng:" +Flag.userFlag.getFullName().toString()+ 
	        		"\n Account :"+Flag.userFlag.getUserName().toString()+
	        		"\n Role :"+Flag.userFlag.getRole().getRoleName().toString()+
	        		" \n Địa chỉ IP đăng nhập : " + ip+
	        		" \n Hostname : " + hostname +
	        		" \n Lấy danh sách LinkUrl");
	        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, serviceList);
	    }
}
