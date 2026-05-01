package com.assessment.ewallet.controller;

import com.assessment.ewallet.dto.ResponseDto;
import com.assessment.ewallet.entity.Banner;
import com.assessment.ewallet.service.BannerService;
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
public class BannerController {


    private final Logger logger = LoggerFactory.getLogger(BannerController.class);

    private final BannerService bannerService;



    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
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
            logger.error("Exception in BannerController ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
