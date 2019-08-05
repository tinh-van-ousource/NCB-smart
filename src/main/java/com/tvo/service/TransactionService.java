/**
 * 
 */
package com.tvo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tvo.dto.TransactionDto;
import com.tvo.model.Transaction;
import com.tvo.request.TransactionRequest;

/**
 * @author Ace
 *
 */
@Service
public interface TransactionService {
	List<TransactionDto> findAll();
	
	Transaction save(TransactionRequest request);
}
