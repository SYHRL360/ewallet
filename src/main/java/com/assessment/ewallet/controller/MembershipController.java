package com.assessment.ewallet.controller;

import com.assessment.ewallet.dto.*;
import com.assessment.ewallet.entity.User;
import com.assessment.ewallet.service.JwtService;
import com.assessment.ewallet.service.UserService;
import com.assessment.ewallet.util.RegexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/")
public class MembershipController {


    private final Logger logger = LoggerFactory.getLogger(MembershipController.class);

    private final JwtService jwtService;

    public final AuthenticationProvider authenticationProvider;


    private final UserService userService;

    public MembershipController(JwtService jwtService, AuthenticationProvider authenticationProvider, UserService userService) {
        this.jwtService = jwtService;
        this.authenticationProvider = authenticationProvider;
        this.userService = userService;
    }

    @PostMapping("registration")
    public ResponseEntity<ResponseDto<String>> registration(@RequestBody RegisterDto registerDto) {
        ResponseDto<String> responseRegistration = null;

        if (!RegexUtil.validateEmail(registerDto.getEmail())) {
            responseRegistration = new ResponseDto<>(102, "Paramter email tidak sesuai format", null);
            return new ResponseEntity<>(responseRegistration, HttpStatus.BAD_REQUEST);
        }
        if (registerDto.getPassword().length() < 8) {
            responseRegistration = new ResponseDto<>(102, "Password minilam 8 digit", null);
            return new ResponseEntity<>(responseRegistration, HttpStatus.BAD_REQUEST);
        }
        if (userService.emailAlreadyExist(registerDto.getEmail())) {
            responseRegistration = new ResponseDto<>(102, "Email sudah terdaftar", null);
            return new ResponseEntity<>(responseRegistration, HttpStatus.BAD_REQUEST);
        }
        try {
            if (userService.registerNewUser(registerDto)) {
                responseRegistration = new ResponseDto<>(0, "Registrasi berhasil silahkan login", null);
                return new ResponseEntity<>(responseRegistration, HttpStatus.OK);
            } else {
                responseRegistration = new ResponseDto<>(102, "Gagal melakukan registrasi", null);
                return new ResponseEntity<>(responseRegistration, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Exception in MembershipController ", e);
            responseRegistration = new ResponseDto<>(500, "Exception error : " + e.getMessage(), null);
            return new ResponseEntity<>(responseRegistration, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("login")
    public ResponseEntity<ResponseDto<String>> login(@RequestBody LoginDto loginDto) {
        ResponseDto<String> responseRegistration = null;

        if (!RegexUtil.validateEmail(loginDto.getEmail())) {
            responseRegistration = new ResponseDto<>(102, "Paramter email tidak sesuai format", null);
            return new ResponseEntity<>(responseRegistration, HttpStatus.BAD_REQUEST);
        }
        if (loginDto.getPassword().length() < 8) {
            responseRegistration = new ResponseDto<>(102, "Password minilam 8 digit", null);
            return new ResponseEntity<>(responseRegistration, HttpStatus.BAD_REQUEST);
        }

        try {
            responseRegistration = userService.loginUser(loginDto);
            if (responseRegistration.getStatus() == 0) {
                return new ResponseEntity<>(responseRegistration, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseRegistration, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Exception in MembershipController ", e);
            responseRegistration = new ResponseDto<>(500, "Exception error : " + e.getMessage(), null);
            return new ResponseEntity<>(responseRegistration, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("profile")
    public ResponseEntity<ResponseDto<ProfileDto>> profile() {
        ResponseDto<ProfileDto> responseProfile = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            User currentUser = (User) authentication.getPrincipal();
            ProfileDto userProfile = userService.selectUserByEmail(currentUser.getEmail());
            if (userProfile != null) {
                responseProfile = new ResponseDto<>(0, "Sukses", userProfile);
                return new ResponseEntity<>(responseProfile, HttpStatus.OK);
            } else {
                responseProfile = new ResponseDto<>(102, "Data user profile tidak ditemukan", null);
                return new ResponseEntity<>(responseProfile, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Exception in MembershipController ", e);
            responseProfile = new ResponseDto<>(500, "Exception error : " + e.getMessage(), null);
            return new ResponseEntity<>(responseProfile, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("profile/update")
    public ResponseEntity<ResponseDto<ProfileDto>> updateProfile(@RequestBody UpdateProfileDto updateProfileDto){
        ResponseDto<ProfileDto> responseUpdateProfile = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String emailUser = authentication.getName();
            ProfileDto updateUser = userService.updateFirstNameOrLastNameByEmail(updateProfileDto, emailUser);
            if (updateUser != null) {
                responseUpdateProfile = new ResponseDto<>(0, "Sukses", updateUser);
                return new ResponseEntity<>(responseUpdateProfile, HttpStatus.OK);
            } else {
                responseUpdateProfile = new ResponseDto<>(102, "Gagal melakukan update profile", null);
                return new ResponseEntity<>(responseUpdateProfile, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Exception in MembershipController ", e);
            responseUpdateProfile = new ResponseDto<>(500, "Exception error : "+ e.getMessage(), null);
            return new ResponseEntity<>(responseUpdateProfile, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "profile/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<ProfileDto>> imageProfile(@RequestParam("file")MultipartFile file) {
        ResponseDto<ProfileDto> responseImageProfile = null;
        List<String> allowedExtentions = Arrays.asList("jpeg", "png");
        String fileExtention = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        if (!allowedExtentions.contains(fileExtention)) {
            responseImageProfile = new ResponseDto<>(102, "Format Image tidak sesuai, Format Image yang boleh di upload hanya jpeg dan png", null);
            return new ResponseEntity<>(responseImageProfile, HttpStatus.BAD_REQUEST);
        }
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String emailUser = authentication.getName();
            ProfileDto imageProfile = userService.uploadProfileImage(file, emailUser);
            if (imageProfile != null) {
                responseImageProfile = new ResponseDto<>(0, "Update Profile Image berhasil", imageProfile);
                return new ResponseEntity<>(responseImageProfile, HttpStatus.OK);
            } else {
                responseImageProfile = new ResponseDto<>(102, "Gagal melakukan update image profile", null);
                return new ResponseEntity<>(responseImageProfile, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Exception in MembershipController ", e);
            responseImageProfile = new ResponseDto<>(500, "Exception error : " + e.getMessage(), null);
            return new ResponseEntity<>(responseImageProfile, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
