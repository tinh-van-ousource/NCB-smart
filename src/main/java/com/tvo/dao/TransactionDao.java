/**
 * 
 */
package com.tvo.dao;

import com.tvo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 * @author Ace
 *
 */
@Repository
public interface TransactionDao extends JpaRepository<Transaction, Integer>{
		public Transaction findByTransactionName(String transactionName);
}
