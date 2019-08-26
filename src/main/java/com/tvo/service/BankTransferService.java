package com.tvo.service;

import com.tvo.controllerDto.searchBankTransfer;
import com.tvo.dto.BankTransferDto;
import com.tvo.request.CreateBankTransferRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BankTransferService {
	public List<BankTransferDto> findAll();
	public Page<BankTransferDto> searchBankTransfer(searchBankTransfer searchBankTransfer, Pageable pageable);
	public BankTransferDto createBankTransfer(CreateBankTransferRequest request) ;
}
