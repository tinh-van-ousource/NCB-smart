/**
 * 
 */
package com.tvo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.ParCardSearch;
import com.tvo.dto.ParCardProductDto;
import com.tvo.request.PardCardProductCreate;
import com.tvo.response.ResponeData;
import com.tvo.service.ParCardProductService;

import io.swagger.annotations.Api;

/**
 * @author Ace
 *
 */
@RestController
@RequestMapping(value = "par-card")
@Api(tags = "ParCardProduct")
public class ParCardProductController {
	@Autowired
	ParCardProductService parCardProductService;

	@RequestMapping(value = "img", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImage(@RequestParam String name) throws IOException {
		try {
			Path path = Paths.get(AppConstant.RESOURCE_IMG + name);
			byte[] bytes = Files.readAllBytes(path);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}

	}
	
	private String uploadingIMG(MultipartFile uploadingFile) throws IOException {
		File file = new File(AppConstant.RESOURCE_IMG + uploadingFile.getOriginalFilename());
		file.delete();
		uploadingFile.transferTo(file);
		return uploadingFile.getOriginalFilename();
	}

	@GetMapping(value = "search")
	public ResponeData<Page<ParCardProductDto>> search(@ModelAttribute ParCardSearch searchModel,
			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<ParCardProductDto> dts = parCardProductService.search(searchModel, pageable);
		return new ResponeData<Page<ParCardProductDto>>(AppConstant.SYSTEM_SUCCESS_CODE,
				AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
	}

	@PostMapping(value = "create")
	public ResponeData<String> create(final @RequestParam("IMG") MultipartFile multipartFiles,
			@ModelAttribute("pardCardProductCreate") PardCardProductCreate pardCardProductCreate) {
		try {
			pardCardProductCreate.setFileName(this.uploadingIMG(multipartFiles));
			parCardProductService.create(pardCardProductCreate);
			return new ResponeData<String>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, null);
		} catch (IOException e) {
			return new ResponeData<String>(AppConstant.SYSTEM_ERORR_MESSAGE, AppConstant.SYSTEM_ERORR_MESSAGE, null);
		}
	}

	@PostMapping(value = "edit")
	public ResponeData<String> edit(final @RequestParam("IMG") MultipartFile multipartFiles,
			@ModelAttribute("pardCardProductCreate") PardCardProductCreate pardCardProductCreate) {
		try {
			pardCardProductCreate.setFileName(this.uploadingIMG(multipartFiles));
			parCardProductService.edit(pardCardProductCreate);
			return new ResponeData<String>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, null);
		} catch (IOException e) {
			return new ResponeData<String>(AppConstant.SYSTEM_ERORR_MESSAGE, AppConstant.SYSTEM_ERORR_MESSAGE, null);
		}
	}
	
	@PostMapping(value = "delete")
	public ResponeData<String> delete(@RequestParam String prdCode){
		return  new ResponeData<String>(parCardProductService.delete(prdCode), AppConstant.SYSTEM_SUCCESS_MESSAGE, null);
	}
}
