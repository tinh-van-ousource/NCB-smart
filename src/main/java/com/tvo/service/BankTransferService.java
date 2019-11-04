package com.tvo.service;

import com.tvo.controllerDto.SearchBankTransfer;
import com.tvo.dto.BankTransferDto;
import com.tvo.request.CreateBankTransferRequest;
import com.tvo.request.UpdateBankTransferRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BankTransferService {

    Page<BankTransferDto> search(SearchBankTransfer searchBankTransfer, Pageable pageable);

    BankTransferDto create(CreateBankTransferRequest request);

    BankTransferDto update(UpdateBankTransferRequest request);

    BankTransferDto detail(String bankCode);

    BankTransferDto deActive(String bankCode);
    
    Boolean delete(String bankCode);
}
