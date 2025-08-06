package com.authguard.authguard_user_service.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.authguard.authguard_user_service.dtos.UserLoginRequest;
import com.authguard.authguard_user_service.model.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public String[] validateClient(UserLoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        User client = (User) authenticate.getPrincipal();
        String accesstoken = jwtService.createAccessToken(client);
        String refreshtoken = jwtService.createRefreshToken(client);
        return new String[] { accesstoken, refreshtoken, client.getUserId(), client.getUsername() };
    }
}
