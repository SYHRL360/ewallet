package com.assessment.ewallet.controller;

import com.assessment.ewallet.dto.ResponseDto;
import com.assessment.ewallet.dto.TransactionHistoryDto;
import com.assessment.ewallet.entity.Balance;
import com.assessment.ewallet.entity.Transaction;
import com.assessment.ewallet.service.BalanceService;
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

    private final BalanceService balanceService;

    public TransactionController(TransactionService transactionService, BalanceService balanceService) {
        this.transactionService = transactionService;
        this.balanceService = balanceService;
    }

    @GetMapping("balance")
    public ResponseEntity<ResponseDto<Long>> getCurrentBalance(){
        ResponseDto<Long> responseBalance = null;
        try {
            Long currentBalance = balanceService.selectCurrentBalanceByEmail("");
            responseBalance = new ResponseDto<>(0, "Get balance berhasil", currentBalance);
            return new ResponseEntity<>(responseBalance, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception in TransactionController.getCurrentBalance ", e);
            responseBalance = new ResponseDto<>(500, "Exception error : " + e.getMessage(), null);
            return new ResponseEntity<>(responseBalance, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("topup")
    public ResponseEntity<ResponseDto<Long>> topUpBalance(@RequestBody Long topUpAmount) {
        ResponseDto<Long> responseTopUp = null;
        if (topUpAmount < 0) {
            responseTopUp = new ResponseDto<>(102, "Parameter amount hanya boleh angka dan tidak boleh kecil dari 0", null);
            return new ResponseEntity<>(responseTopUp, HttpStatus.BAD_REQUEST);
        }
        try {
            Balance balanceParam = new Balance("", topUpAmount);
            Long currentBalance = balanceService.topUpBalance(balanceParam);
            responseTopUp = new ResponseDto<>(0, "Top Up Balance berhasil", currentBalance);
            return new ResponseEntity<>(responseTopUp, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception in TransactionController.topUpBalance ", e);
            responseTopUp = new ResponseDto<>(500, "Exception error : " + e.getMessage(), null);
            return new ResponseEntity<>(responseTopUp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
            responseTransaction = new ResponseDto<>(500, "Exception error : " + e.getMessage(), null);
            return new ResponseEntity<>(responseTransaction, HttpStatus.INTERNAL_SERVER_ERROR);
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
            responseHistory = new ResponseDto<>(500, "Exception error : " + e.getMessage(), null);
            return new ResponseEntity<>(responseHistory, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
