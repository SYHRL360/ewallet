package com.assessment.ewallet.service;

import com.assessment.ewallet.dto.ResponseDto;
import com.assessment.ewallet.dto.TransactionHistoryDto;
import com.assessment.ewallet.entity.Transaction;

public interface TransactionService {

    ResponseDto<Transaction> doTransaction(String serviceCode, String email);

    TransactionHistoryDto getAllTransactionByOffset(Integer offset, Integer size);
}
