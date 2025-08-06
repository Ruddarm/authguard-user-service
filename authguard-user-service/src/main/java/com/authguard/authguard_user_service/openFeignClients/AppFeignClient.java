package com.authguard.authguard_user_service.openFeignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="authguard-app-service", path="/apps")
public interface  AppFeignClient {
    @GetMapping("/name")
    public String getAppName();
}
