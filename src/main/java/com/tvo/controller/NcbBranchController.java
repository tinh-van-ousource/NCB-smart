package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.common.ModelMapperUtils;
import com.tvo.controllerDto.SearchNcbBranchModel;
import com.tvo.dto.NcbActiveBranchOnlyResDto;
import com.tvo.dto.NcbBranchDto;
import com.tvo.model.NcbBranch;
import com.tvo.request.CreateNcbBranchRequest;
import com.tvo.request.UpdateNcbBranchRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.NcbBranchService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Thanglt
 * @version 1.0
 * @date Aug 8, 2019
 */
@RestController
@RequestMapping(value = "ncb-branch")
@Api(tags = "Ncb Branch")
public class NcbBranchController {
    @Autowired
    private NcbBranchService ncbBranchService;

    @GetMapping(value = "search")
    public ResponeData<Page<NcbBranchDto>> searchBranch(SearchNcbBranchModel searchModel,
                                                        @PageableDefault(size = AppConstant.LIMIT_PAGE) Pageable pageable) {
        Page<NcbBranchDto> res = ncbBranchService.searchNcbBranch(searchModel, pageable);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, res);
    }

    @GetMapping(value = "branch/activated-list")
    public ResponeData<List<NcbActiveBranchOnlyResDto>> getAllActivatedBranch() {
		List<NcbActiveBranchOnlyResDto> res = ncbBranchService.getAllActivatedBranch();
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, res);
    }

    @GetMapping(value = "detail")
    public ResponeData<NcbBranchDto> detail(@RequestParam String departCode) {
        if (StringUtils.isEmpty(departCode.trim())) {
            return new ResponeData<NcbBranchDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        NcbBranch ncbBranch = ncbBranchService.findByDepartCode(departCode);
        NcbBranchDto result = ModelMapperUtils.map(ncbBranch, NcbBranchDto.class);
        return new ResponeData<NcbBranchDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
                result);

    }

    @PostMapping(value = "create")
    public ResponeData<NcbBranchDto> create(@RequestBody CreateNcbBranchRequest request) {
        NcbBranchDto ncbBranch = ncbBranchService.create(request);
        if (ncbBranch == null) {
            return new ResponeData<NcbBranchDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        return new ResponeData<NcbBranchDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
                ncbBranch);
    }

    @PutMapping(value = "update")
    public ResponeData<NcbBranchDto> update(@RequestBody UpdateNcbBranchRequest request) {
        NcbBranchDto ncbBranch = ncbBranchService.update(request);
        if (ncbBranch == null) {
            return new ResponeData<NcbBranchDto>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
        }
        return new ResponeData<NcbBranchDto>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE,
                ncbBranch);
    }

    @DeleteMapping(value = "delete")
    public ResponeData<Boolean> delete(@RequestParam String departCode) {
        Boolean deleteFlag = ncbBranchService.delete(departCode);
        if (deleteFlag == true) {
            return new ResponeData<Boolean>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, true);
        }
        return new ResponeData<Boolean>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, false);
    }
}
