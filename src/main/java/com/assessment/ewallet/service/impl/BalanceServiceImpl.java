package com.assessment.ewallet.service.impl;

import com.assessment.ewallet.entity.Balance;
import com.assessment.ewallet.repository.BalanceRepository;
import com.assessment.ewallet.service.BalanceService;
import org.springframework.stereotype.Service;

@Service
public class BalanceServiceImpl implements BalanceService {


    private final BalanceRepository balanceRepository;

    public BalanceServiceImpl(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Override
    public int insertBalance(Balance balance) {
        return balanceRepository.insert(balance);
    }

    @Override
    public int updateBalance(Balance balance) {
        return balanceRepository.updateBalance(balance);
    }

    @Override
    public long topUpBalance(Balance balanceParam) {
        Balance currentBalance = balanceRepository.selectByEmail(balanceParam.getEmail());
        currentBalance.setBalance(currentBalance.getBalance() + balanceParam.getBalance());
        return currentBalance.getBalance();
    }

    @Override
    public long selectCurrentBalanceByEmail(String email) {
        Balance currentBalance = balanceRepository.selectByEmail(email);
        return currentBalance.getBalance();
    }
}
