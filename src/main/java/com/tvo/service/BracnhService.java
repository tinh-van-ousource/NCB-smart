/**
 * 
 */
package com.tvo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tvo.controllerDto.searchBranch;
import com.tvo.dto.BranchDto;
import com.tvo.request.CreateBranchRequest;

/**
 * @author Ace
 *
 */
public interface BracnhService {
		public List<BranchDto> findAll();
		public BranchDto createBranch(CreateBranchRequest request) ;
		public Page<BranchDto> searchBranch(searchBranch searchBranch, Pageable pageable);
}
