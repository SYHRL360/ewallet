package com.assessment.ewallet.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Transaction {

    private String invoiceNumber;
    private String transactionType;
    private String description;
    private long totalAmount;
    private Date createdOn;

}
