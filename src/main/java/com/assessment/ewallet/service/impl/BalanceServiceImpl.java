package com.assessment.ewallet.service.impl;

import com.assessment.ewallet.entity.Balance;
import com.assessment.ewallet.entity.Transaction;
import com.assessment.ewallet.repository.BalanceRepository;
import com.assessment.ewallet.repository.TransactionRepository;
import com.assessment.ewallet.service.BalanceService;
import com.assessment.ewallet.util.DateUtil;
import com.assessment.ewallet.util.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class BalanceServiceImpl implements BalanceService {


    private final BalanceRepository balanceRepository;

    private final TransactionRepository transactionRepository;

    public BalanceServiceImpl(BalanceRepository balanceRepository, TransactionRepository transactionRepository) {
        this.balanceRepository = balanceRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public long topUpBalance(Balance balanceParam) {

        Transaction topUpTransaction = new Transaction();
        topUpTransaction.setInvoiceNumber("INV" + DateUtil.getNowDateForTransaction() + "-" + NumberUtil.generate2Number());
        topUpTransaction.setTransactionType("TOPUP");
        topUpTransaction.setDescription("Top Up Balance");
        topUpTransaction.setTotalAmount(balanceParam.getBalance());
        topUpTransaction.setCreatedOn(new Date(DateUtil.getTimeNowLongFormat()));
        transactionRepository.insert(topUpTransaction);

        Balance currentBalance = balanceRepository.selectByEmail(balanceParam.getEmail());
        if (currentBalance == null || StringUtils.isEmpty(currentBalance.getEmail())) {
            balanceRepository.insert(balanceParam);
            return balanceParam.getBalance();
        }


        currentBalance.setBalance(currentBalance.getBalance() + balanceParam.getBalance());
        balanceRepository.updateBalance()
        return currentBalance.getBalance();
    }

    @Override
    public long selectCurrentBalanceByEmail(String email) {
        Balance currentBalance = balanceRepository.selectByEmail(email);
        return currentBalance.getBalance();
    }
}
