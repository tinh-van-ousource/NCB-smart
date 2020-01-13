package com.tvo.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchNcbBranchModel;
import com.tvo.dto.CompDroplistBranchDto;
import com.tvo.dto.NcbActiveBranchOnlyResDto;
import com.tvo.dto.NcbActiveDepartOnlyResDto;
import com.tvo.dto.NcbBranchDto;
import com.tvo.model.NcbBranch;
import com.tvo.request.CreateNcbBranchRequest;
import com.tvo.request.UpdateNcbBranchRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.NcbBranchService;

/**
 * @author Thanglt
 * @version 1.0
 * @date Aug 8, 2019
 */
@RestController
@RequestMapping(value = "ncb-branch")
public class NcbBranchController {
    @Autowired
    private NcbBranchService ncbBranchService;
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @GetMapping(value = "search")
    public ResponeData<Page<NcbBranchDto>> searchBranch(SearchNcbBranchModel searchModel,
                                                        @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        Page<NcbBranchDto> res = ncbBranchService.searchNcbBranch(searchModel, pageable);
        logger.info("Xóa Banner");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, res);
    }

    @GetMapping(value = "branch/activated-list")
    public ResponeData<List<NcbActiveBranchOnlyResDto>> getAllActivatedBranch() {
		List<NcbActiveBranchOnlyResDto> res = ncbBranchService.getAllActivatedBranch();
		logger.info("Xóa Banner");
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, res);
    }
    
    @GetMapping(value = "branch/list-comp-codename")
    public ResponeData<List<CompDroplistBranchDto>> getDroplistCodeName() {
		List<CompDroplistBranchDto> res = ncbBranchService.getCompDroplist();
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, res);
    }
    @GetMapping(value = "depart/activated-list")
    public ResponeData<List<NcbActiveDepartOnlyResDto>> getAllActivatedDepart() {
        List<NcbActiveDepartOnlyResDto> res = ncbBranchService.getAllActivatedDepart();
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, res);
    }

    @GetMapping(value = "detail")
    public ResponeData<NcbBranchDto> detail(@RequestParam String departCode) {
        if (StringUtils.isEmpty(departCode.trim())) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        NcbBranch ncbBranch = ncbBranchService.findByDepartCode(departCode);
        NcbBranchDto result = ModelMapperUtils.map(ncbBranch, NcbBranchDto.class);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
                result);
    }

    @PostMapping(value = "create")
    public ResponeData<NcbBranchDto> create(@RequestBody CreateNcbBranchRequest request) {
        NcbBranchDto ncbBranch = ncbBranchService.create(request);
        if (ncbBranch == null) {
            return new ResponeData<>(AppConstant.BRANCH_EXISTED_CODE, AppConstant.BRANCH_EXISTED_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
                ncbBranch);
    }

    @PutMapping(value = "update")
    public ResponeData<NcbBranchDto> update(@RequestBody UpdateNcbBranchRequest request) {
        NcbBranchDto ncbBranch = ncbBranchService.update(request);
        if (ncbBranch == null) {
            return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
                ncbBranch);
    }

    @DeleteMapping(value = "delete")
    public ResponeData<Boolean> delete(@RequestParam String departCode) {
        Boolean deleteFlag = ncbBranchService.delete(departCode);
        if (deleteFlag) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
    }
}
