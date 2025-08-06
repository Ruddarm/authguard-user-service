package com.authguard.authguard_user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authguard.authguard_user_service.openFeignClients.AppFeignClient;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class demo {
    
    private final AppFeignClient appFeingClient;

    @GetMapping("name")
    public String getName(){
        return "Name is Fuck Man";
    }

    @GetMapping("app/name")
    public String getAppName(){
        System.out.println("Inside app name");
        return appFeingClient.getAppName();
    }
}
