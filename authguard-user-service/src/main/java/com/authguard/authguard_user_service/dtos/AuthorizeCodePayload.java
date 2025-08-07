package com.authguard.authguard_user_service.dtos;

import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthorizeCodePayload implements  Serializable {
    private UUID userID;
    private String client_Id;
    private UUID appUserLinkId;
    private String nonce;
}
