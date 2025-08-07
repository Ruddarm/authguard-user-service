package com.authguard.authguard_user_service.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authguard.authguard_user_service.Exception.ResourceException;
import com.authguard.authguard_user_service.dtos.ClientAppRequest;
import com.authguard.authguard_user_service.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;

    @GetMapping("/Username")
    public String getName() {
        return "Name is Fuck Man";
    }

    @PostMapping("/oauth2/code")
    public ResponseEntity<Map<String, String>> getCodeForApp(@Valid @RequestBody ClientAppRequest clientAppRequest)
            throws ResourceException, JsonProcessingException {

        // String code =
        // appUserAuthService.genterateCode(clientAppRequest.getClient_id(),
        // clientAppRequest.getNonce(),
        // userAuth.getUserId());
        String code = authService.generateCode(clientAppRequest);
        // redisService.saveAuthCode(code,
        // AuthCodePayload.builder().client_id(clientAppRequest.getClient_id())
        // .userId(userAuth.getUserId()).build());
        URI redirectUrl = URI.create(clientAppRequest.getRedirectUrl() + "?code=" + code);
        System.out.println(redirectUrl);
        // return ResponseEntity.status(HttpStatus.FOUND).location(redirectUrl).build();
        Map<String, String> response = new HashMap<>();
        response.put("code", code);
        response.put("redirecturi", clientAppRequest.getRedirectUrl());
        return ResponseEntity.ok(response);

    }
}
