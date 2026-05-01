package com.assessment.ewallet.dto;

import com.assessment.ewallet.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryDto {

    private String offset;
    private String limit;
    private List<Transaction> record;
}
