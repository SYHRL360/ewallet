package com.assessment.ewallet.controller;



import com.assessment.ewallet.dto.ResponseDto;
import com.assessment.ewallet.entity.Balance;
import com.assessment.ewallet.service.BalanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class BalanceController {

    private final Logger logger = LoggerFactory.getLogger(BannerController.class);

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @Operation(summary = "Get loan record by user id", description = "Return loan record by specific user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "if loan record not exists")
    })
    @GetMapping("balance")
    public ResponseEntity<ResponseDto<Long>> getCurrentBalance(){
        ResponseDto<Long> responseBalance = null;
        try {
            Long currentBalance = balanceService.selectCurrentBalanceByEmail("");
            responseBalance = new ResponseDto<>(0, "Get balance berhasil", currentBalance);
            return new ResponseEntity<>(responseBalance, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception in BalanceController.getCurrentBalance ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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
            logger.error("Exception in BalanceController.topUpBalance ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
