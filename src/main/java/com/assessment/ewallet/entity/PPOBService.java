package com.assessment.ewallet.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class PPOBService {

    @NotEmpty
    private String serviceCode;
    private String serviceName;
    private String serviceIcon;
    private long serviceTariff;


}


