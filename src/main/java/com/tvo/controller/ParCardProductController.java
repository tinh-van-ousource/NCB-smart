/**
 * 
 */
package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.ParCardSearch;
import com.tvo.dto.ParCardProductDto;
import com.tvo.model.ParCardProduct;
import com.tvo.request.PardCardProductCreate;
import com.tvo.response.ResponeData;
import com.tvo.response.UploadFileResponse;
import com.tvo.service.ParCardProductService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Ace
 *
 */
@RestController
@RequestMapping(value = "par-card")
@Api(tags = "ParCardProduct")
public class ParCardProductController {
	@Autowired
	private ParCardProductService parCardProductService;

	@RequestMapping(value = "img", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImage(final @RequestParam("img") MultipartFile multipartFiles) throws IOException {
		try {
			Path path = Paths.get(AppConstant.RESOURCE_IMG + multipartFiles.getOriginalFilename());
			byte[] bytes = Files.readAllBytes(path);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "search")
	public ResponeData<Page<ParCardProductDto>> search(@ModelAttribute ParCardSearch searchModel,
			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<ParCardProductDto> dts = parCardProductService.search(searchModel, pageable);
		return new ResponeData<Page<ParCardProductDto>>(AppConstant.SYSTEM_SUCCESS_CODE,
				AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
	}

	@GetMapping(value = "detail")
	public ResponeData<ParCardProductDto> detail(@RequestParam String prdcode) {
		if (StringUtils.isEmpty(prdcode.trim())) {
			return new ResponeData<ParCardProductDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE,
					null);
		}
		ParCardProduct parCardProduct = parCardProductService.findPrdcode(prdcode);
		ParCardProductDto result = ModelMapperUtils.map(parCardProduct, ParCardProductDto.class);
		return new ResponeData<ParCardProductDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				result);
	}

	@PostMapping(value = "create")
	public ResponeData<ParCardProductDto> create(final @RequestParam("img") MultipartFile multipartFiles,
			@ModelAttribute PardCardProductCreate pardCardProductCreate) {
		try {
			UploadFileResponse uploadFileResponse = parCardProductService.uploadFile(multipartFiles);
			pardCardProductCreate.setLinkUlr(uploadFileResponse.getFileUploadUri());
			pardCardProductCreate.setFileName(uploadFileResponse.getFileName());
			ParCardProductDto parCardProduc = ModelMapperUtils.map(parCardProductService.create(pardCardProductCreate),
					ParCardProductDto.class);
			return new ResponeData<ParCardProductDto>(AppConstant.SYSTEM_SUCCESS_CODE,
					AppConstant.SYSTEM_SUCCESS_MESSAGE, parCardProduc);
		} catch (Exception e) {
			return new ResponeData<ParCardProductDto>(AppConstant.SYSTEM_ERROR_MESSAGE,
					AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
	}

	@PostMapping(value = "edit")
	public ResponeData<ParCardProductDto> edit(final @RequestParam("IMG") MultipartFile multipartFiles,
			@RequestBody PardCardProductCreate pardCardProductCreate) {
		try {
			Path path = Paths.get(AppConstant.RESOURCE_IMG + multipartFiles.getOriginalFilename());
			pardCardProductCreate.setLinkUlr(path.toString());
			pardCardProductCreate.setFileName(path.getFileName().toString());
			ParCardProductDto parCardProduc = ModelMapperUtils.map(parCardProductService.edit(pardCardProductCreate),
					ParCardProductDto.class);
			return new ResponeData<ParCardProductDto>(AppConstant.SYSTEM_SUCCESS_CODE,
					AppConstant.SYSTEM_SUCCESS_MESSAGE, parCardProduc);
		} catch (Exception e) {
			return new ResponeData<ParCardProductDto>(AppConstant.SYSTEM_ERROR_MESSAGE,
					AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
	}

	@PostMapping(value = "delete")
	public ResponeData<String> delete(@RequestParam String prdCode) {
		return new ResponeData<String>(parCardProductService.delete(prdCode), AppConstant.SYSTEM_SUCCESS_MESSAGE, null);
	}
}
