package com.authguard.authguard_user_service.ServiceTestUT;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import com.authguard.authguard_user_service.service.JwtService;
import com.authguard.authguard_user_service.service.UserService;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private  JwtService jwtService;

    @Mock 
    private AuthenticationManager  authenticationManager;    

    @InjectMocks
    private UserService userService;

    @Test
    void testValidateUser_whenUserIsValid_thenReturnArrayOfString(){
        
    }
    
}



































