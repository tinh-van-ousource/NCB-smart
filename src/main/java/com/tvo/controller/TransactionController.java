/**
 * 
 */
package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.dto.TransactionDto;
import com.tvo.request.TransactionRequest;
import com.tvo.response.ResponeData;
import com.tvo.service.TransactionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.util.List;

/**
 * @author Ace
 *
 */
@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	InetAddress ip;
    String hostname;
    
	@Autowired TransactionService transactionService;
	
	@GetMapping(value = "/getAll")
	public ResponeData<List<TransactionDto>> getAll(){
		return  new  ResponeData<List<TransactionDto>>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, transactionService.findAll());
	}

	@PostMapping(value = "/create-trans")
	public ResponeData<TransactionDto> createTransaction(@ModelAttribute TransactionRequest requesst){
		return null;
	}
}
