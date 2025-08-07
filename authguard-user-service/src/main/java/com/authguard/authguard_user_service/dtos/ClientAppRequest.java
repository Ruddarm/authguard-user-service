package com.authguard.authguard_user_service.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientAppRequest {
    
    @NotBlank
    @NotNull
    public String client_Id;
    @NotBlank
    @NotNull
    public String nonce;
    @NotBlank
    @NotNull
    public String redirectUrl;
}
