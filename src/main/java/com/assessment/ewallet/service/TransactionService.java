package com.assessment.ewallet.service;

import com.assessment.ewallet.entity.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction doTransaction(String serviceCode, String email);

    List<Transaction> getAllTransactionByOffset(int offset, int size);
}
