package com.assessment.ewallet.service;

import com.assessment.ewallet.dto.LoginDto;
import com.assessment.ewallet.dto.RegisterDto;
import com.assessment.ewallet.dto.ResponseDto;
import com.assessment.ewallet.entity.User;

public interface UserService {

    boolean emailAlreadyExist(String email);

    boolean registerNewUser(RegisterDto registerDto);


    ResponseDto<String> loginUser(LoginDto loginDto);
}
