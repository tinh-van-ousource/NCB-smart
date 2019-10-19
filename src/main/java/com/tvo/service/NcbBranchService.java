package com.tvo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.SearchNcbBranchModel;
import com.tvo.dto.CompDroplistBranchDto;
import com.tvo.dto.NcbActiveBranchOnlyResDto;
import com.tvo.dto.NcbActiveDepartOnlyResDto;
import com.tvo.dto.NcbBranchDto;
import com.tvo.model.NcbBranch;
import com.tvo.request.CreateNcbBranchRequest;
import com.tvo.request.UpdateNcbBranchRequest;

/**
 * @author Thanglt
 * @version 1.0
 * @date Aug 8, 2019
 */
public interface NcbBranchService {
    public List<NcbBranchDto> findAll();

    public NcbBranch findByDepartCode(String departCode);

    public Page<NcbBranchDto> searchNcbBranch(SearchNcbBranchModel searchModel, Pageable pageable);

    public NcbBranchDto update(UpdateNcbBranchRequest request);

    public NcbBranchDto create(CreateNcbBranchRequest request);

    public Boolean delete(String brnCode);

    List<NcbActiveBranchOnlyResDto> getAllActivatedBranch();

    List<NcbActiveDepartOnlyResDto> getAllActivatedDepart();
    
    List<CompDroplistBranchDto> getCompDroplist();
}
