package com.assessment.ewallet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private String invoiceNumber;
    private String transactionType;
    private String description;
    private long totalAmount;
    private LocalDateTime createdOn;

}
