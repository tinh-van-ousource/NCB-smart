package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchParamManagerModel;
import com.tvo.dto.ParamManagerDto;
import com.tvo.enums.StatusActivate;
import com.tvo.model.ParamManager;
import com.tvo.request.CreateParamManagerRequest;
import com.tvo.request.UpdateParamManagerRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.ParamManagerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping(value = "param-manager")
public class ParamManagerController {

	@Autowired
	private ParamManagerService paramManagerService;

	@GetMapping(value = "search")
	public ResponeData<Page<ParamManagerDto>> searchDatUserProfile(@ModelAttribute SearchParamManagerModel searchModel,
			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<ParamManagerDto> dts = paramManagerService.searchParamManager(searchModel, pageable);
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
	}

	@GetMapping(value = "detail")
	public ResponeData<ParamManagerDto> detail(@RequestParam String paramNo) {
		if (StringUtils.isEmpty(paramNo.trim())) {
			return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		ParamManager paramManager = paramManagerService.findByParamNo(paramNo);
		ParamManagerDto result = ModelMapperUtils.map(paramManager, ParamManagerDto.class);
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
	}

	@PostMapping(value = "create")
	public ResponeData<ParamManager> create(@RequestBody CreateParamManagerRequest request) {
		ParamManager paramManager = paramManagerService.create(request);
		if (paramManager == null) {
			return new ResponeData<>(AppConstant.PARAM_MANAGER_EXISTED_CODE, AppConstant.PARAM_MANAGER_EXISTED_MESSAGE, null);
		}
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, paramManager);
	}

	@PutMapping(value = "update")
	public ResponeData<ParamManager> update(@RequestBody UpdateParamManagerRequest request) {
		ParamManager paramManager = paramManagerService.update(request);
		if (paramManager == null) {
			return new ResponeData<>(AppConstant.SYSTEM_ERROR_MESSAGE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, paramManager);
	}

	@DeleteMapping(value = "delete")
	public ResponeData<Boolean> delete(@RequestParam String paramNo) {
		boolean deleteFlag = paramManagerService.delete(paramNo);
		if (deleteFlag) {
			return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
		}
		return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
	}

	@PostMapping(value = "/create/uploadFile")
	public ResponeData<List<ParamManager>> submit(@RequestParam("file") MultipartFile file) {
		System.out.println("file : " + file);
		try {
			FileInputStream excelFile = new FileInputStream(convert(file));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);

			// Remove rows null
			int lastIndex = datatypeSheet.getLastRowNum();
			for (int i = 0; i <= lastIndex; i++) {
				if (datatypeSheet.getRow(i) == null || datatypeSheet.getRow(i).getCell(0) == null || datatypeSheet.getRow(i).getCell(0).toString().equals("")){
					datatypeSheet.removeRow(datatypeSheet.getRow(i));
				}
			}

			Iterator<Row> iterator = datatypeSheet.iterator();
			Row firstRow = iterator.next();
			Cell firstCell = firstRow.getCell(0);
			System.out.println(firstCell.getStringCellValue());
			List<ParamManagerDto> paramManagerDtoList = new ArrayList<>();
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				ParamManagerDto paramManagerDto = new ParamManagerDto();
				paramManagerDto.setParamNo(currentRow.getCell(0).getStringCellValue());
				paramManagerDto.setParamName(currentRow.getCell(1).getStringCellValue());
				paramManagerDto.setParamValue(currentRow.getCell(2).getStringCellValue());
				paramManagerDto.setNote(currentRow.getCell(3).getStringCellValue());
				paramManagerDto.setStatus(StatusActivate.STATUS_ACTIVATED.getStatus());
				paramManagerDtoList.add(paramManagerDto);
			}
			List<ParamManager> paramManagers = paramManagerService.saveAll(paramManagerDtoList);
			return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, paramManagers);
		} catch (IOException e) {
			return new ResponeData<>(AppConstant.SYSTEM_ERROR_MESSAGE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
	}

	private static File convert(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

}
