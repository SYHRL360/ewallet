package com.assessment.ewallet.service;

import com.assessment.ewallet.dto.ResponseDto;
import com.assessment.ewallet.dto.TransactionHistoryDto;
import com.assessment.ewallet.entity.Transaction;

import java.util.List;

public interface TransactionService {

    ResponseDto<Transaction> doTransaction(String serviceCode, String email);

    TransactionHistoryDto getAllTransactionByOffset(int offset, int size);
}
