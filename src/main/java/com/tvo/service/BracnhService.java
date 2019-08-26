/**
 * 
 */
package com.tvo.service;

import com.tvo.controllerDto.searchBranch;
import com.tvo.dto.BranchDto;
import com.tvo.request.CreateBranchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Ace
 *
 */
public interface BracnhService {
		public List<BranchDto> findAll();
		public BranchDto createBranch(CreateBranchRequest request) ;
		public Page<BranchDto> searchBranch(searchBranch searchBranch, Pageable pageable);
}
