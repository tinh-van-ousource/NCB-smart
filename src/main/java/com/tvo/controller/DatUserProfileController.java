package com.tvo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchConsumerModel;
import com.tvo.controllerDto.SearchDatUserProfileModel;
import com.tvo.dto.DatUserProfileDto;
import com.tvo.response.ResponeData;
import com.tvo.service.DatUserProfileService;

import io.swagger.annotations.Api;

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
	@Autowired
	private DatUserProfileService datUserProfileService;

	@GetMapping(value = "searchUser")
	public ResponeData<Page<DatUserProfileDto>> searchDatUserProfile(@RequestBody SearchDatUserProfileModel searchModel,
			@RequestParam(value = "filter") String filter,
			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<DatUserProfileDto> dts = datUserProfileService.searchDatUserProfile(searchModel, filter, pageable);
		return new ResponeData<Page<DatUserProfileDto>>(AppConstant.SYSTEM_SUCCESS_CODE,
				AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
	}

	@GetMapping(value = "searchConsumer")
	public ResponeData<Page<DatUserProfileDto>> searchConsumer(@RequestBody SearchConsumerModel searchModel,
			@RequestParam(value = "filter") String filter,
			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
		Page<DatUserProfileDto> dts = datUserProfileService.searchConsumer(searchModel, filter, pageable);
		return new ResponeData<Page<DatUserProfileDto>>(AppConstant.SYSTEM_SUCCESS_CODE,
				AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
	}
//	@RequestMapping(method = RequestMethod.GET, value = "/filter")
//	public ResponeData<Page<DatUserProfileDto>> filter(@RequestParam(value = "search", required = false) String search,
//			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
//		List<SearchCriteria> params = new ArrayList<SearchCriteria>();
//		if (search != null) {
//			Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
//			Matcher matcher = pattern.matcher(search + ",");
//			while (matcher.find()) {
////				params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
//			}
//		}
//		Page<DatUserProfileDto> dts = datUserProfileService.filterUser(params, pageable);
//		return new ResponeData<Page<DatUserProfileDto>>(AppConstant.SYSTEM_SUCCESS_CODE,
//				AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
//	}
//
//	@GetMapping(value = "fill")
//	public ResponeData<Page<DatUserProfileDto>> search(@RequestParam(value = "search", required = false) String search,
//			@PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
//		List<SearchCriteria> params = new ArrayList<SearchCriteria>();
//		if (search != null) {
//			Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
//			Matcher matcher = pattern.matcher(search + ",");
//			System.out.println("check boolean: " + matcher.find());
//			while (matcher.find()) {
//				params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
//			}
//		}
//		Page<DatUserProfileDto> dts = datUserProfileService.search(params, pageable);
//		return new ResponeData<Page<DatUserProfileDto>>(AppConstant.SYSTEM_SUCCESS_CODE,
//				AppConstant.SYSTEM_SUCCESS_MESSAGE, dts);
//	}

//	@RequestMapping(value = "/filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	@Transactional(readOnly = true)
//	public ResponeData<Page<DatUserProfileDto>> getAllUser(Pageable pageable, @RequestBody String filters)
//			throws URISyntaxException, JSONException {
//
//		JSONObject sfilters = null;
//		try {
//			sfilters = new JSONObject(filters);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//
//		DatUserProfileSpecification spec = new DatUserProfileSpecification(sfilters);
//
//		Page<DatUserProfileDto> page = datUserProfileService.findAll(spec, pageable);
//
//		return new ResponeData<Page<DatUserProfileDto>>(AppConstant.SYSTEM_SUCCESS_CODE,
//				AppConstant.SYSTEM_SUCCESS_MESSAGE, page);
//
//	}
}