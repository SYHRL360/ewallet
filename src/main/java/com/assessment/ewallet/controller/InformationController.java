package com.assessment.ewallet.controller;

import com.assessment.ewallet.dto.ResponseDto;
import com.assessment.ewallet.entity.Banner;
import com.assessment.ewallet.entity.PPOBService;
import com.assessment.ewallet.service.BannerService;
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
public class InformationController {
    private final Logger logger = LoggerFactory.getLogger(InformationController.class);

    private final BannerService bannerService;

    private final PPOBServiceService ppobServiceService;

    public InformationController(BannerService bannerService, PPOBServiceService ppobServiceService) {
        this.bannerService = bannerService;
        this.ppobServiceService = ppobServiceService;
    }

    @GetMapping("banner")
    public ResponseEntity<ResponseDto<List<Banner>>> getAllBanner() {
        ResponseDto<List<Banner>> responseBanner = null;
        try {
            List<Banner> bannerList = bannerService.getAllBanner();
            if (bannerList != null) {
                responseBanner = new ResponseDto<>(0,"Sukses",bannerList);
                return new ResponseEntity<>(responseBanner, HttpStatus.OK);
            } else {
                responseBanner = new ResponseDto<>(102, "Data banner kosong", null);
                return new ResponseEntity<>(responseBanner, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Exception in InformationController.banner ", e);
            responseBanner = new ResponseDto<>(500, "Exception error : " + e.getMessage(), null);
            return new ResponseEntity<>(responseBanner, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
            logger.error("Exception in InformationController.services ", e);
            responsePPOBService = new ResponseDto<>(500, "Exception error : " + e.getMessage(), null);
            return new ResponseEntity<>(responsePPOBService, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
