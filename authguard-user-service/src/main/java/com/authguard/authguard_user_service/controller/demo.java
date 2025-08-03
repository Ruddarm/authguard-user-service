package com.authguard.authguard_user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class demo {
    
    @GetMapping("name")
    public String getName(){
        return "Name is Fuck Man";
    }
}
