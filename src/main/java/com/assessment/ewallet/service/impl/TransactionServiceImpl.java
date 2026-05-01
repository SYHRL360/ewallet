package com.assessment.ewallet.service.impl;

import com.assessment.ewallet.dto.ResponseDto;
import com.assessment.ewallet.dto.TransactionHistoryDto;
import com.assessment.ewallet.entity.Balance;
import com.assessment.ewallet.entity.PPOBService;
import com.assessment.ewallet.entity.Transaction;
import com.assessment.ewallet.repository.BalanceRepository;
import com.assessment.ewallet.repository.TransactionRepository;
import com.assessment.ewallet.service.PPOBServiceService;
import com.assessment.ewallet.service.TransactionService;
import com.assessment.ewallet.util.DateUtil;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
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
    public ResponseDto<Transaction> doTransaction(String serviceCode, String email) {

        PPOBService availablePPOB = ppobServiceService.selectByServiceCode(serviceCode);
        if (availablePPOB == null) {
            return new ResponseDto<>(102, "Service atau layanan tidak ditemukan", null);
        }

        Balance currentBalance = balanceRepository.selectByEmail(email);
        if (currentBalance == null || currentBalance.getBalance() < availablePPOB.getServiceTariff()) {
            return new ResponseDto<>(103, "Balance tidak mencukupi harap top up balance terlebih dahulu", null);
        }

        Transaction paymentTransaction = new Transaction();
        paymentTransaction.setInvoiceNumber("INV" + DateUtil.getNowDateForTransaction() + "-" + RandomUtils.secure().randomInt(10, 99));
        paymentTransaction.setTransactionType("PAYMENT");
        paymentTransaction.setDescription(availablePPOB.getServiceName());
        paymentTransaction.setTotalAmount(availablePPOB.getServiceTariff());
        paymentTransaction.setCreatedOn(new Date(DateUtil.getTimeNowLongFormat()));
        transactionRepository.insert(paymentTransaction);
        // update balance
        currentBalance.setEmail(email);
        currentBalance.setBalance(currentBalance.getBalance() - paymentTransaction.getTotalAmount());
        balanceRepository.updateBalance(currentBalance);
        return new ResponseDto<>(0, "Transaksi berhasil", paymentTransaction);
    }

    @Override
    public TransactionHistoryDto getAllTransactionByOffset(int offset, int size) {

        List<Transaction> transactionList = new ArrayList<>();
        transactionList = transactionRepository.selectAllTransactionOffset(offset, size);
        TransactionHistoryDto transactionHistoryDto = new TransactionHistoryDto();
        transactionHistoryDto.setOffset(String.valueOf(offset));
        transactionHistoryDto.setLimit(String.valueOf(size));
        transactionHistoryDto.setRecord(transactionList);
        return transactionHistoryDto;
    }
}
