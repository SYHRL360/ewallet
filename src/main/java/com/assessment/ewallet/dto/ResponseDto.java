package com.assessment.ewallet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDto<T> {

    private int status;

    private String message;

    private T data;


}
