package com.assessment.ewallet.controller;

import com.assessment.ewallet.dto.*;
import com.assessment.ewallet.entity.Balance;
import com.assessment.ewallet.entity.Transaction;
import com.assessment.ewallet.entity.User;
import com.assessment.ewallet.service.BalanceService;
import com.assessment.ewallet.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();

            Long currentBalance = balanceService.selectCurrentBalanceByEmail(currentUser.getEmail());
            responseBalance = new ResponseDto<>(0, "Get balance berhasil", currentBalance);
            return new ResponseEntity<>(responseBalance, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception in TransactionController.getCurrentBalance ", e);
            responseBalance = new ResponseDto<>(500, "Exception error : " + e.getMessage(), null);
            return new ResponseEntity<>(responseBalance, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("topup")
    public ResponseEntity<ResponseDto<Long>> topUpBalance(@RequestBody TopUpDto topUpDto) {
        ResponseDto<Long> responseTopUp = null;
        if (topUpDto.getTopUpAmount() < 0) {
            responseTopUp = new ResponseDto<>(102, "Parameter amount hanya boleh angka dan tidak boleh kecil dari 0", null);
            return new ResponseEntity<>(responseTopUp, HttpStatus.BAD_REQUEST);
        }
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String emailUser = authentication.getName();

            Balance balanceParam = new Balance(emailUser, topUpDto.getTopUpAmount());
            Long currentBalance = balanceService.topUpBalance(balanceParam);
            responseTopUp = new ResponseDto<>(0, "Top Up Balance berhasil", currentBalance);
            return new ResponseEntity<>(responseTopUp, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception in TransactionController.topUpBalance ", e);
            responseTopUp = new ResponseDto<>(500, "Exception error : " + e.getMessage(), null);
            return new ResponseEntity<>(responseTopUp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "Input serviceCode yang tersedia : (PAJAK, PLN, PDAM, PULSA, PGN, MUSIK, TV, PAKET_DATA, VOUCHER_GAME, VOUCHER_MAKAN, QURBAN, ZAKAT)")
    @PostMapping("transaction")
    public ResponseEntity<ResponseDto<Transaction>> doTransaction(@RequestBody TransactionDto transactionDto) {
        ResponseDto<Transaction> responseTransaction = null;

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String emailUser = authentication.getName();

            responseTransaction = transactionService.doTransaction(transactionDto.getServiceCode(), emailUser);
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
    public ResponseEntity<ResponseDto<TransactionHistoryDto>> transactionHistory(@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit) {
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
