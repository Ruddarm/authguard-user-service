package com.authguard.authguard_user_service.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authguard.authguard_user_service.Exception.ResourceException;
import com.authguard.authguard_user_service.dtos.ClientAppRequest;
import com.authguard.authguard_user_service.dtos.UserLoginRequest;
import com.authguard.authguard_user_service.dtos.UserLoginResponse;
import com.authguard.authguard_user_service.dtos.UserSingupRequest;
import com.authguard.authguard_user_service.service.AuthService;
import com.authguard.authguard_user_service.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/user")
@RequiredArgsConstructor
public class UserAuthController {

    // private final AppFeignClient appFeingClient;
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserLoginResponse> signupUser(@Valid @RequestBody UserSingupRequest userSingupRequest)
            throws ResourceException {

        return new ResponseEntity<>(userService.createUser(userSingupRequest), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> cliengLoging(@Valid @RequestBody UserLoginRequest loginRequest,
            HttpServletRequest request, HttpServletResponse response) {
        String[] data = authService.validateClient(loginRequest);
        String cookie = String.format(
                "client_refresh_token=%s; Path=/; HttpOnly; SameSite=Lax; Max-Age=%d",
                data[1], 7 * 24 * 60 * 60);
        response.setHeader("Set-Cookie", cookie);
        return ResponseEntity
                .ok(UserLoginResponse.builder().accessToken(data[0]).userId(data[2]).email(data[3]).build());
    }
    
  

    // @GetMapping("app/name")
    // public String getAppName(){
    // System.out.println("Inside app name");
    // return appFeingClient.getAppName();
    // }
}
