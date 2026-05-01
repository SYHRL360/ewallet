package com.assessment.ewallet.controller;

import com.assessment.ewallet.dto.ResponseDto;
import com.assessment.ewallet.entity.PPOBService;
import com.assessment.ewallet.service.PPOBServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class PPOBServiceController {

    private final Logger logger = LoggerFactory.getLogger(PPOBServiceController.class);

    private final PPOBServiceService ppobServiceService;

    public PPOBServiceController(PPOBServiceService ppobServiceService) {
        this.ppobServiceService = ppobServiceService;
    }

    @GetMapping("services")
    public ResponseEntity<ResponseDto<List<PPOBService>>> getAllPPOBService(){
        ResponseDto<List<PPOBService>> responsePPOBService = null;
        try {
            List<PPOBService> ppobServiceList = ppobServiceService.getAllPPOBService();
            if (ppobServiceList != null) {
                responsePPOBService = new ResponseDto<>(0,"Sukses", ppobServiceList);
                return new ResponseEntity<>(responsePPOBService, HttpStatus.OK);
            } else {
                responsePPOBService = new ResponseDto<>(102, "Data service PPOB kosong", null);
                return new ResponseEntity<>(responsePPOBService, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Exception in PPOBServiceController ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
