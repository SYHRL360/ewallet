package com.assessment.ewallet.exception;

import com.assessment.ewallet.dto.ResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<ResponseDto<String>> handleException(Exception exception) {
        ResponseDto<String> responseDto = null;
        ResponseEntity<ResponseDto<String>> responseEntity = null;



        if (exception instanceof BadCredentialsException) {
            responseDto = new ResponseDto<>(103, "Username atau password salah", null);
            responseEntity = new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
        }
        if (exception instanceof AccessDeniedException) {
            responseDto = new ResponseDto<>(108, "Anda tidak memiliki hak akses", null);
            responseEntity = new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
        }
        if (exception instanceof SignatureException) {
            responseDto = new ResponseDto<>(108, "Token JWT Invalid", null);
            responseEntity = new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
        }
        if (exception instanceof ExpiredJwtException) {
            responseDto = new ResponseDto<>(108, "Token tidak valid atau kadaluwarsa", null);
            responseEntity = new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
        }

        if (responseEntity == null) {
            responseDto = new ResponseDto<>(103, "Internal server error.", null);
            responseEntity = new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

}
