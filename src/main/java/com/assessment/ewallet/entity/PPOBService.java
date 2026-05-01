package com.assessment.ewallet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class PPOBService {

    private String serviceCode;

    private String serviceName;

    private String serviceIcon;

    private long serviceTariff;


}


