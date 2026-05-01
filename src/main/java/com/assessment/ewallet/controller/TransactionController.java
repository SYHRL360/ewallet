package com.assessment.ewallet.controller;

import com.assessment.ewallet.dto.ResponseDto;
import com.assessment.ewallet.dto.TransactionHistoryDto;
import com.assessment.ewallet.entity.Transaction;
import com.assessment.ewallet.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class TransactionController {

    private final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("transaction")
    public ResponseEntity<ResponseDto<Transaction>> doTransaction(@RequestBody String serviceCode) {
        ResponseDto<Transaction> responseTransaction = null;
        try {
            responseTransaction = transactionService.doTransaction(serviceCode, "");
            if (responseTransaction.getStatus() == 102 || responseTransaction.getStatus() == 103) {
                return new ResponseEntity<>(responseTransaction, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(responseTransaction,HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Exception in TransactionController.transaction ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("history")
    public ResponseEntity<ResponseDto<TransactionHistoryDto>> transactionHistory(@RequestParam Integer offset, @RequestParam Integer limit) {
        ResponseDto<TransactionHistoryDto> responseHistory = null;
        try {
            TransactionHistoryDto transactionHistoryDto = transactionService.getAllTransactionByOffset(offset, limit);
            if (transactionHistoryDto != null){
                responseHistory = new ResponseDto<>(0, "Get History Berhasil", transactionHistoryDto);
                return new ResponseEntity<>(responseHistory,HttpStatus.OK);
            } else {
                responseHistory = new ResponseDto<>(102, "Data history transaksi kosong", null);
                return new ResponseEntity<>(responseHistory, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Exception in TransactionController.history ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
