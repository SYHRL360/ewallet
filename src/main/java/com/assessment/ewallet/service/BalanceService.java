package com.assessment.ewallet.service;

import com.assessment.ewallet.entity.Balance;

public interface BalanceService {

    Long topUpBalance(Balance balanceParam);

    long selectCurrentBalanceByEmail(String email);
}
