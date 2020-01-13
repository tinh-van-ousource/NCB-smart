package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.common.DateTimeUtil;
import com.tvo.controllerDto.SearchConsumerModel;
import com.tvo.controllerDto.SearchDatUserProfileModel;
import com.tvo.dto.DatUserProfileDto;
import com.tvo.response.ResponeData;
import com.tvo.service.DatUserProfileService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Thanglt
 *
 * @version 1.0
 * @date Aug 8, 2019
 */
@RestController
@RequestMapping(value = "user-profile")
@Api(tags = "DatUserProfile")
public class DatUserProfileController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private DatUserProfileService datUserProfileService;

	@GetMapping(value = "searchUser")
	public ResponeData<Page<DatUserProfileDto>> search(@ModelAttribute SearchDatUserProfileModel searchFunction, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable){
		Page<DatUserProfileDto> datUserProfileDtos = datUserProfileService.searchDatUserProfile(searchFunction, pageable);
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, datUserProfileDtos) ;
	}

	@GetMapping(value = "searchConsumer")
	public ResponeData<Page<DatUserProfileDto>> searchConsumer(@ModelAttribute SearchConsumerModel searchModel,@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<DatUserProfileDto> dts = datUserProfileService.searchConsumer(searchModel, pageable);
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);

}}