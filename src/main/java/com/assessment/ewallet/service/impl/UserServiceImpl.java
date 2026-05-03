package com.assessment.ewallet.service.impl;

import com.assessment.ewallet.dto.*;
import com.assessment.ewallet.entity.User;
import com.assessment.ewallet.repository.UserRepository;
import com.assessment.ewallet.service.JwtService;
import com.assessment.ewallet.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public boolean emailAlreadyExist(String email) {
        return userRepository.findByEmail(email).isPresent() ;
    }

    @Override
    public boolean registerNewUser(RegisterDto registerDto) {
        RegisterDto newUser = new RegisterDto();
        newUser.setEmail(registerDto.getEmail());
        newUser.setFirstName(registerDto.getFirstName());
        newUser.setLastName(registerDto.getLastName());
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        return  userRepository.insert(newUser) > 0;
    }

    @Override
    public ProfileDto selectUserByEmail(String email) {
        return userRepository.findProfileInfo(email);
    }

    @Override
    public ResponseDto<String> loginUser(LoginDto loginDto) {
        String token = "";
        try{
            User authenticatedUser = authenticate(loginDto);

            token = jwtService.generateToken(authenticatedUser);

        } catch (UsernameNotFoundException e) {
            return new ResponseDto<>(103, "Username atau password salah", null);
        }

        return new ResponseDto<>(0, "Login Sukses", token);
    }

    @Override
    public ProfileDto updateFirstNameOrLastNameByEmail(UpdateProfileDto newUpdateProfileDto, String email) {

        ProfileDto updateProfile = selectUserByEmail(email);

        updateProfile.setFirstName(StringUtils.isNotEmpty(newUpdateProfileDto.getFirstName()) ? newUpdateProfileDto.getFirstName() : updateProfile.getFirstName());
        updateProfile.setLastName(StringUtils.isNotEmpty(newUpdateProfileDto.getLastName()) ? newUpdateProfileDto.getLastName() : updateProfile.getLastName());

        return userRepository.updateProfileName(updateProfile) > 0 ? updateProfile : null ;
    }

    @Override
    public ProfileDto uploadProfileImage(MultipartFile file, String email) throws Exception {
        String uploadDir = "/uploads";
        Path path = Paths.get(uploadDir, file.getOriginalFilename());
        // Create directory 'uploads' if not exits
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        String uploadedImagePath = path.toString();

        ProfileDto updateProfile = selectUserByEmail(email);
        updateProfile.setProfileImage(uploadedImagePath);

        return userRepository.updateProfileImage(updateProfile) > 0 ? updateProfile : null;
    }

    private User authenticate(LoginDto loginUser) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));

        return userRepository.findByEmail(loginUser.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
