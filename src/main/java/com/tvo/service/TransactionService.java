/**
 * 
 */
package com.tvo.service;

import com.tvo.dto.TransactionDto;
import com.tvo.model.Transaction;
import com.tvo.request.TransactionRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ace
 *
 */
@Service
public interface TransactionService {
	List<TransactionDto> findAll();
	
	Transaction save(TransactionRequest request);
}
