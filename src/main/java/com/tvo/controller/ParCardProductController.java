package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.ParCardSearch;
import com.tvo.dto.ParCardProductResDto;
import com.tvo.model.ParCardProductEntity;
import com.tvo.request.ParCardProductCreateReqDto;
import com.tvo.response.ResponeData;
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

@RestController
@RequestMapping(value = "par-card")
@Api(tags = "ParCardProductEntity")
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
	public ResponeData<Page<ParCardProductResDto>> search(@ModelAttribute ParCardSearch searchModel,
														  @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<ParCardProductResDto> dts = parCardProductService.search(searchModel, pageable);
		return new ResponeData<Page<ParCardProductResDto>>(AppConstant.SYSTEM_SUCCESS_CODE,
				AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
	}

	@GetMapping(value = "detail")
	public ResponeData<ParCardProductResDto> detail(@RequestParam String prdcode) {
		if (StringUtils.isEmpty(prdcode.trim())) {
			return new ResponeData<ParCardProductResDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE,
					null);
		}
		ParCardProductEntity parCardProductEntity = parCardProductService.findPrdcode(prdcode);
		ParCardProductResDto result = ModelMapperUtils.map(parCardProductEntity, ParCardProductResDto.class);
		return new ResponeData<ParCardProductResDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
				result);
	}

	@PostMapping(value = "edit")
	public ResponeData<ParCardProductResDto> edit(final @RequestParam("IMG") MultipartFile multipartFiles,
												  @RequestBody ParCardProductCreateReqDto parCardProductCreateReqDto) {
		try {
			Path path = Paths.get(AppConstant.RESOURCE_IMG + multipartFiles.getOriginalFilename());
			parCardProductCreateReqDto.setLinkUrl(path.toString());
			parCardProductCreateReqDto.setFileName(path.getFileName().toString());
			ParCardProductResDto parCardProduc = ModelMapperUtils.map(parCardProductService.edit(parCardProductCreateReqDto),
					ParCardProductResDto.class);
			return new ResponeData<ParCardProductResDto>(AppConstant.SYSTEM_SUCCESS_CODE,
					AppConstant.SYSTEM_SUCCESS_MESSAGE, parCardProduc);
		} catch (Exception e) {
			return new ResponeData<ParCardProductResDto>(AppConstant.SYSTEM_ERROR_MESSAGE,
					AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
	}

	@PostMapping(value = "delete")
	public ResponeData<String> delete(@RequestParam String prdCode) {
		return new ResponeData<String>(parCardProductService.delete(prdCode), AppConstant.SYSTEM_SUCCESS_MESSAGE, null);
	}

	@PostMapping(value = "create")
	public ResponeData<ParCardProductResDto> create(final @RequestParam("img") MultipartFile multipartFiles,
													@ModelAttribute ParCardProductCreateReqDto parCardProductCreateReqDto) {
		try {
			ParCardProductResDto parCardProduct = parCardProductService.create(multipartFiles, parCardProductCreateReqDto);
			if (parCardProduct != null) {
				return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, parCardProduct);
			}
			return new ResponeData<>(AppConstant.PAR_CARD_EXISTED_CODE, AppConstant.PAR_CARD_EXISTED_MESSAGE, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
	}
}
