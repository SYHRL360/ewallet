package com.assessment.ewallet.service;

import com.assessment.ewallet.entity.PPOBService;

import java.util.List;

public interface PPOBServiceService {


    List<PPOBService> getAllPPOBService();

    PPOBService selectByServiceCode(String serviceCode);
}
