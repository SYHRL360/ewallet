package com.assessment.ewallet.service;

import com.assessment.ewallet.dto.*;

public interface UserService {

    boolean emailAlreadyExist(String email);

    boolean registerNewUser(RegisterDto registerDto);


    ProfileDto selectUserByEmail(String email);

    ResponseDto<String> loginUser(LoginDto loginDto);

    ProfileDto updateFirstNameOrLastNameByEmail(UpdateProfileDto updateProfileDto, String email);
}
