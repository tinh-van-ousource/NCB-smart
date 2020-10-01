package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.config.Flag;
import com.tvo.controllerDto.SearchParamManagerModel;
import com.tvo.dto.ParamManagerDto;
import com.tvo.model.ConfigMbApp;
import com.tvo.model.ParamManager;
import com.tvo.request.CreateParamManagerRequest;
import com.tvo.request.UpdateParamManagerRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.ParamManagerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping(value = "param-manager")
public class ParamManagerController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	InetAddress ip;
    String hostname;
    
	private static final String HOT_LINE = "HOTLINE";

	@Autowired
	private ParamManagerService paramManagerService;

	@GetMapping(value = "search")
	public ResponeData<Page<ParamManagerDto>> searchDatUserProfile(@ModelAttribute SearchParamManagerModel searchModel,
			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<ParamManagerDto> dts = paramManagerService.searchParamManager(searchModel, pageable);
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
        		" \n Tìm kiếm Số tổng đài");
		return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
	}

	@GetMapping(value = "detail")
	public ResponeData<ParamManagerDto> detail(@RequestParam Long id) {
		ConfigMbApp configMbApp = paramManagerService.findByIdAndCode(id);
		if (configMbApp != null) {
			ParamManagerDto result = ModelMapperUtils.map(configMbApp, ParamManagerDto.class);
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
	        		" \n Chi tiết Số tổng đài");
			return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
		}
		return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
	}

	@PostMapping(value = "create")
	public ResponeData<ParamManagerDto> create(@RequestBody CreateParamManagerRequest request) {
		ConfigMbApp configMbApp = paramManagerService.create(request);
		if (configMbApp != null) {
			ParamManagerDto result = ModelMapperUtils.map(configMbApp, ParamManagerDto.class);
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
	        		" \n Tạo mới Số tổng đài");
			return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
		}
		return new ResponeData<>(AppConstant.PARAM_MANAGER_EXISTED_CODE, AppConstant.PARAM_MANAGER_EXISTED_MESSAGE, null);
	}

	@PutMapping(value = "update")
	public ResponeData<ParamManagerDto> update(@RequestBody UpdateParamManagerRequest request) {
		ConfigMbApp configMbApp = paramManagerService.update(request);
		if (configMbApp != null) {
			ParamManagerDto result = ModelMapperUtils.map(configMbApp, ParamManagerDto.class);
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
	        		" \n Cập nhật thông tin Số tổng đài");
			return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
		}
		return new ResponeData<>(AppConstant.SYSTEM_ERROR_MESSAGE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
	}

	@DeleteMapping(value = "delete")
	public ResponeData<Boolean> delete(@RequestParam Long id) {
		boolean deleteFlag = paramManagerService.delete(id);
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
	        		" \n Xóa Số tổng đài");
			return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.DELETED_SUCCESS_MESSAGE, true);
		}
		return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
	}

	@PostMapping(value = "/create/uploadFile")
	public ResponeData<List<ParamManagerDto>> submit(@RequestParam("file") MultipartFile file) {
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
				//paramManagerDto.setCode(currentRow.getCell(0).getStringCellValue());
				paramManagerDto.setCode(HOT_LINE);
				paramManagerDto.setName(currentRow.getCell(0).getStringCellValue());
				paramManagerDto.setValue(currentRow.getCell(1).getStringCellValue());
				paramManagerDto.setDescription(currentRow.getCell(2).getStringCellValue());
				paramManagerDtoList.add(paramManagerDto);
			}
			List<ConfigMbApp> paramManagers = paramManagerService.saveAll(paramManagerDtoList);
			List<ParamManagerDto> result = ModelMapperUtils.mapAll(paramManagers, ParamManagerDto.class);
			return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, result);
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