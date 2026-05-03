package com.assessment.ewallet.service;

import com.assessment.ewallet.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {


    boolean emailAlreadyExist(String email);


    boolean registerNewUser(RegisterDto registerDto);


    ProfileDto selectUserByEmail(String email);

    ResponseDto<String> loginUser(LoginDto loginDto);

    ProfileDto updateFirstNameOrLastNameByEmail(UpdateProfileDto newUpdateProfileDto, String email);

    ProfileDto uploadProfileImage(MultipartFile file, String email) throws Exception;
}
