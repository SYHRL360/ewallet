package com.assessment.ewallet.service.impl;

import com.assessment.ewallet.dto.*;
import com.assessment.ewallet.entity.User;
import com.assessment.ewallet.repository.UserRepository;
import com.assessment.ewallet.service.JwtService;
import com.assessment.ewallet.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        return  userRepository.insert(registerDto) > 0;
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
    public ProfileDto updateFirstNameOrLastNameByEmail(UpdateProfileDto updateProfileDto, String email) {



        ProfileDto updateProfile = new ProfileDto();
        updateProfile.setFirstName(updateProfile.getFirstName());
        updateProfile.setLastName(updateProfile.getLastName());
        updateProfile.setEmail(email);

        return userRepository.updateProfileName(updateProfile);
    }

    private User authenticate(LoginDto loginUser) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));

        return userRepository.findByEmail(loginUser.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
