package com.authguard.authguard_user_service.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.authguard.authguard_user_service.Context.UserContext;
import com.authguard.authguard_user_service.Exception.ResourceException;
import com.authguard.authguard_user_service.dtos.AuthorizeCodePayload;
import com.authguard.authguard_user_service.dtos.ClientAppRequest;
import com.authguard.authguard_user_service.dtos.UserLoginRequest;
import com.authguard.authguard_user_service.model.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final CacheManager cacheManager;
    private final ObjectMapper objectMapper;
    private static final SecureRandom random = new SecureRandom();
    private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

    public String[] validateClient(UserLoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        User client = (User) authenticate.getPrincipal();
        String accesstoken = jwtService.createAccessToken(client);
        String refreshtoken = jwtService.createRefreshToken(client);
        return new String[] { accesstoken, refreshtoken, client.getUserId(), client.getUsername() };
    }

    public String generateCode(ClientAppRequest appRequest) throws ResourceException, JsonProcessingException {
        // TODO : Validate App
        UUID userId = UserContext.getUserId();
        User user = userService.loadByUserId(userId);
        // TODO : Chech app Link activity
        String authCode = generate();
        // String payload = objectMapper.writeValueAsString(
        //         );
        cacheManager.getCache("AuthorizeCode").put(authCode,
                AuthorizeCodePayload.builder().userId(userId).client_Id(appRequest.getClient_Id()).appUserLinkId(null)
                        .nonce(appRequest.getNonce()).build());
        return authCode;
    }

    public static String generate() {
        byte[] bytes = new byte[24];
        random.nextBytes(bytes);
        return encoder.encodeToString(bytes);
    }
}
