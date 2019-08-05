package com.tvo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.searchBankTransfer;
import com.tvo.controllerDto.searchBranch;
import com.tvo.dto.BankTransferDto;
import com.tvo.dto.BranchDto;
import com.tvo.request.CreateBankTransferRequest;
import com.tvo.request.CreateBranchRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.BankTransferServiceImpl;

@RestController
@RequestMapping(value = "/bankTransfer")
public class BankTransferController {
	@Autowired
	BankTransferServiceImpl bankService; 
	@GetMapping(value = "/getAll")
	public ResponeData<List<BankTransferDto>>  getAll(){
		return new ResponeData<List<BankTransferDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, bankService.findAll());
	};
	@GetMapping(value = "/searchBankTransfer")
	public ResponeData<Page<BankTransferDto>> searchCity(@ModelAttribute searchBankTransfer searchBankTransfer, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable){
		 Page<BankTransferDto> BankTransferDtos = bankService.searchBankTransfer(searchBankTransfer, pageable);
		return new ResponeData<Page<BankTransferDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, BankTransferDtos) ;
	}
	@PostMapping(value="/createBankTransfer")
	public ResponeData<BankTransferDto> createBankTransfer(@ModelAttribute CreateBankTransferRequest request) {
		BankTransferDto dto = bankService.createBankTransfer(request);
		if(dto == null) {
			return new ResponeData<BankTransferDto>(AppConstant.SYSTEM_ERORR_CODE, AppConstant.SYSTEM_ERORR_MESSAGE, null);
		}
		return new ResponeData<BankTransferDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	}
}
