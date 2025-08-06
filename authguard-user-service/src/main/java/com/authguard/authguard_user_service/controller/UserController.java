package com.authguard.authguard_user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authguard.authguard_user_service.openFeignClients.AppFeignClient;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.authguard.authguard_user_service.Exception.ResourceException;
import com.authguard.authguard_user_service.dtos.UserLoginRequest;
import com.authguard.authguard_user_service.dtos.UserLoginResponse;
import com.authguard.authguard_user_service.dtos.UserSingupRequest;
import com.authguard.authguard_user_service.service.AuthService;
import com.authguard.authguard_user_service.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    // private final AppFeignClient appFeingClient;
    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/Username")
    public String getName() {
        return "Name is Fuck Man";
    }

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
