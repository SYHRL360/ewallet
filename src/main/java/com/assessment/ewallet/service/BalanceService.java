package com.assessment.ewallet.service;

import com.assessment.ewallet.entity.Balance;

public interface BalanceService {



    int insertBalance(Balance balance);

    int updateBalance(Balance balance);

    long topUpBalance(Balance balanceParam);

    long selectCurrentBalanceByEmail(String email);
}
