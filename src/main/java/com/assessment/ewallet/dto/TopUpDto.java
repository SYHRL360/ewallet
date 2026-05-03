package com.assessment.ewallet.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TopUpDto {
    @NotNull
    private Long topUpAmount;
}
