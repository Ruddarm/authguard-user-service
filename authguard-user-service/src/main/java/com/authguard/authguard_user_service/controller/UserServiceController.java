package com.authguard.authguard_user_service.controller;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authguard.authguard_user_service.Exception.ResourceException;
import com.authguard.authguard_user_service.dtos.UserResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.authguard.authguard_user_service.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/service/user")
@RequiredArgsConstructor
public class UserServiceController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getMethodName(@PathVariable UUID userId)
            throws JsonProcessingException, ResourceException {
        return ResponseEntity.ok(modelMapper.map(userService.loadByUserId(userId), UserResponse.class));
    }

}
