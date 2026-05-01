package com.assessment.ewallet.service.impl;

import com.assessment.ewallet.entity.PPOBService;
import com.assessment.ewallet.repository.PPOBServiceRepository;
import com.assessment.ewallet.service.PPOBServiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PPOBServiceImpl implements PPOBServiceService {


    private final PPOBServiceRepository ppobServiceRepository;

    public PPOBServiceImpl(PPOBServiceRepository ppobServiceRepository) {
        this.ppobServiceRepository = ppobServiceRepository;
    }

    @Override
    public List<PPOBService> getAllPPOBService() {
        return ppobServiceRepository.selectAllPPOBService();
    }

    @Override
    public PPOBService selectByServiceCode(String serviceCode) {
        return ppobServiceRepository.selectByServiceCode(serviceCode);
    }
}
