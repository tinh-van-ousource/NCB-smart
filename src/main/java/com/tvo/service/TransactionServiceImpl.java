/**
 * 
 */
package com.tvo.service;

import com.tvo.common.ModelMapperUtils;
import com.tvo.dao.TransactionDao;
import com.tvo.dto.TransactionDto;
import com.tvo.model.Transaction;
import com.tvo.request.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ace
 *
 */
@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	TransactionDao transactionDao;
	@Override
	public List<TransactionDto> findAll() {
		List<Transaction> transactions = transactionDao.findAll();
		return ModelMapperUtils.mapAll(transactions, TransactionDto.class);
	}
	@Override
	public Transaction save(TransactionRequest request) {
		Transaction save = transactionDao.save(ModelMapperUtils.map(request, Transaction.class));
		return save;
	}
		
}
