package com.assessment.ewallet.service.impl;

import com.assessment.ewallet.entity.PPOBService;
import com.assessment.ewallet.entity.Transaction;
import com.assessment.ewallet.service.PPOBServiceService;
import com.assessment.ewallet.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final Transaction transaction;

    private final PPOBServiceService ppobServiceService;


    private final TransactionService transactionService;

    public TransactionServiceImpl(Transaction transaction, PPOBServiceService ppobServiceService, TransactionService transactionService) {
        this.transaction = transaction;
        this.ppobServiceService = ppobServiceService;
        this.transactionService = transactionService;
    }

    @Override
    public Transaction doTransaction(String serviceCode) {

        PPOBService availablePPOB = ppobServiceService.selectByServiceCode(serviceCode);
        if (availablePPOB == null) {
            return null;
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setInvoiceNumber("INV");
        newTransaction.setTransactionType("PAYMENT");



        return null;
    }

    @Override
    public List<Transaction> getAllTransactionByOffset(int offset, int size) {
        return null;
    }
}
