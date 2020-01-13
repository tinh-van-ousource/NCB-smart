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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/bank-transfer")
public class BankTransferController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    BankTransferServiceImpl bankService;

    @GetMapping(value = "/search")
    public ResponeData<Page<BankTransferDto>> search(@ModelAttribute SearchBankTransfer searchBankTransfer, @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        Page<BankTransferDto> dto = bankService.search(searchBankTransfer, pageable);
        logger.info("Tìm kiếm ngân hàng chuyển khoản");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
    }

    @GetMapping(value = "/detail")
    public ResponeData<BankTransferDto> detail(@RequestParam String bankCode) {
        BankTransferDto dto = bankService.detail(bankCode);
        if (dto == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        logger.info("Chi tiết ngân hàng chuyển khoản");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
    }

    @PostMapping(value = "/create")
    public ResponeData<BankTransferDto> create(@RequestBody CreateBankTransferRequest request) {
        BankTransferDto dto = bankService.create(request);
        if (dto == null) {
            return new ResponeData<>(AppConstant.BANK_TRANSFER_EXISTED_CODE, AppConstant.BANK_TRANSFER_EXISTED_MESSAGE, null);
        }
        logger.info("Tạo mới ngân hàng chuyển khoản");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
    }

    @PutMapping(value = "/update")
    public ResponeData<BankTransferDto> update(@RequestBody UpdateBankTransferRequest request) {
        BankTransferDto dto = bankService.update(request);
        if (dto == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        logger.info("Cập nhật thông tin ngân hàng chuyển khoản");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
    }

    @DeleteMapping(value = "/deActive")
    public ResponeData<BankTransferDto> deActive(@RequestParam String bankCode) {
        BankTransferDto dto = bankService.deActive(bankCode);
        if (dto == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, dto);
    }
    @DeleteMapping(value = "/delete")
    public ResponeData<Boolean> delete(@RequestParam String bankCode) {
        Boolean resDto = bankService.delete(bankCode);
        logger.info("Xóa ngân hàng chuyển khoản");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, resDto);
    }

}
