package com.tvo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.searchBankTransfer;
import com.tvo.dto.BankTransferDto;
import com.tvo.dto.BranchDto;
import com.tvo.request.CreateBankTransferRequest;

public interface BankTransferService {
	public List<BankTransferDto> findAll();
	public Page<BankTransferDto> searchBankTransfer(searchBankTransfer searchBankTransfer, Pageable pageable);
	public BankTransferDto createBankTransfer(CreateBankTransferRequest request) ;
}
