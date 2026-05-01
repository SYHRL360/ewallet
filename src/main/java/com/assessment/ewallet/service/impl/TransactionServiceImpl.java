package com.assessment.ewallet.service.impl;

import com.assessment.ewallet.entity.Balance;
import com.assessment.ewallet.entity.PPOBService;
import com.assessment.ewallet.entity.Transaction;
import com.assessment.ewallet.repository.BalanceRepository;
import com.assessment.ewallet.repository.TransactionRepository;
import com.assessment.ewallet.service.PPOBServiceService;
import com.assessment.ewallet.service.TransactionService;
import com.assessment.ewallet.util.DateUtil;
import com.assessment.ewallet.util.NumberUtil;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final PPOBServiceService ppobServiceService;

    private final BalanceRepository balanceRepository;


    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  PPOBServiceService ppobServiceService,
                                  BalanceRepository balanceRepository) {
        this.transactionRepository = transactionRepository;
        this.ppobServiceService = ppobServiceService;
        this.balanceRepository = balanceRepository;
    }

    @Override
    public Transaction doTransaction(String serviceCode, String email) {

        PPOBService availablePPOB = ppobServiceService.selectByServiceCode(serviceCode);
        if (availablePPOB == null) {
            return null;
        }

        Transaction paymentTransaction = new Transaction();
        paymentTransaction.setInvoiceNumber("INV" + DateUtil.getNowDateForTransaction() + "-" + NumberUtil.generate2Number());
        paymentTransaction.setTransactionType("PAYMENT");
        paymentTransaction.setDescription(availablePPOB.getServiceName());
        paymentTransaction.setTotalAmount(availablePPOB.getServiceTariff());
        paymentTransaction.setCreatedOn(new Date(DateUtil.getTimeNowLongFormat()));
        transactionRepository.insert(paymentTransaction);
        // update balance
        Balance currentBalance = balanceRepository.selectByEmail(email);
        currentBalance.setEmail(email);
        currentBalance.setBalance(currentBalance.getBalance() - paymentTransaction.getTotalAmount());
        balanceRepository.updateBalance(currentBalance);
        return paymentTransaction;
    }

    @Override
    public List<Transaction> getAllTransactionByOffset(int offset, int size) {
        return null;
    }
}
