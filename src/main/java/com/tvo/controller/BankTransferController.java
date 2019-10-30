package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.controllerDto.SearchBankTransfer;
import com.tvo.dto.BankTransferDto;
import com.tvo.request.CreateBankTransferRequest;
import com.tvo.request.UpdateBankTransferRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.BankTransferServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bank-transfer")
public class BankTransferController {

    @Autowired
    BankTransferServiceImpl bankService;

    @GetMapping(value = "/search")
    public ResponeData<Page<BankTransferDto>> search(@ModelAttribute SearchBankTransfer searchBankTransfer, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        Page<BankTransferDto> dto = bankService.search(searchBankTransfer, pageable);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
    }

    @GetMapping(value = "/detail")
    public ResponeData<BankTransferDto> detail(@RequestParam String bankCode) {
        BankTransferDto dto = bankService.detail(bankCode);
        if (dto == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
    }

    @PostMapping(value = "/create")
    public ResponeData<BankTransferDto> create(@RequestBody CreateBankTransferRequest request) {
        BankTransferDto dto = bankService.create(request);
        if (dto == null) {
            return new ResponeData<>(AppConstant.BANK_TRANSFER_EXISTED_CODE, AppConstant.BANK_TRANSFER_EXISTED_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
    }

    @PutMapping(value = "/update")
    public ResponeData<BankTransferDto> update(@RequestBody UpdateBankTransferRequest request) {
        BankTransferDto dto = bankService.update(request);
        if (dto == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
    }

    @DeleteMapping(value = "/deActice")
    public ResponeData<BankTransferDto> delete(@RequestParam String bankCode) {
        BankTransferDto dto = bankService.delete(bankCode);
        if (dto == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
    }
    @DeleteMapping(value = "/delete")
    public ResponeData<Boolean> deActive(@RequestParam String bankCode) {
        Boolean resDto = bankService.deActice(bankCode);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, resDto);
    }

}
