package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchBankTransfer;
import com.tvo.dto.BankTransferDto;
import com.tvo.request.CreateBankTransferRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.BankTransferServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	public ResponeData<Page<BankTransferDto>> searchCity(@ModelAttribute SearchBankTransfer searchBankTransfer, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable){
		 Page<BankTransferDto> BankTransferDtos = bankService.searchBankTransfer(searchBankTransfer, pageable);
		return new ResponeData<Page<BankTransferDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, BankTransferDtos) ;
	}
	@PostMapping(value="/createBankTransfer")
	public ResponeData<BankTransferDto> createBankTransfer(@ModelAttribute CreateBankTransferRequest request) {
		BankTransferDto dto = bankService.createBankTransfer(request);
		if(dto == null) {
			return new ResponeData<BankTransferDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
		}
		return new ResponeData<BankTransferDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
	}
}
