/**
 * 
 */
package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchBranch;
import com.tvo.dto.BranchDto;
import com.tvo.request.CreateBranchRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.BracnhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ace
 *
 */
@RestController
@RequestMapping(value = "/branch")
public class BranchController {
	@Autowired
	BracnhService bracnhService;
	
	@GetMapping(value = "/getAll")
	public ResponeData<List<BranchDto>>  getAll(){
		return new ResponeData<List<BranchDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, bracnhService.findAll());
	};
	@PostMapping(value="/createBranch")
	public ResponeData<BranchDto> createBranch(@ModelAttribute CreateBranchRequest request) {
		BranchDto dto = bracnhService.createBranch(request);
		if(dto == null) {
			return new ResponeData<BranchDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<BranchDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	}
	@GetMapping(value = "/searchBranch")
	public ResponeData<Page<BranchDto>> searchCity(@ModelAttribute SearchBranch searchBranch, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable){
		 Page<BranchDto> BranchDtos = bracnhService.searchBranch(searchBranch, pageable);
		return new ResponeData<Page<BranchDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, BranchDtos) ;
	}
}
