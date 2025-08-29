package com.authguard.authguard_user_service.ControllerTestIT;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.authguard.authguard_user_service.config.TestContainerConfig;
import com.authguard.authguard_user_service.dtos.UserLoginResponse;
import com.authguard.authguard_user_service.dtos.UserSingupRequest;
import com.authguard.authguard_user_service.repository.UserRepository;

@AutoConfigureWebTestClient(timeout="10000000")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfig.class)
public class UserAuthControllerIT {
    
    @Autowired
    private WebTestClient webTestClient;
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void Test(){

    }

    @BeforeEach
    public void Setup(){
        userRepository.deleteAll();
    }

    @Test
    public void testSignupUser_whenEmailNotExist_ThenReturnUserLoginResponse(){
        UserSingupRequest singupRequest = UserSingupRequest
                                            .builder()
                                            .firstName("jhon")
                                            .lastName("doe")
                                            .email("jhonDoe@gmail.com")
                                            .password("securedPaswd")
                                            .build();
        
        webTestClient
                        .post()
                        .uri("/auth/user/signup")
                        .bodyValue(singupRequest)
                        .exchange()
                        .expectStatus().isCreated()
                        .expectBody(UserLoginResponse.class)
                        .value((userLoginResponse)->{
                            Assertions.assertThat(userLoginResponse).isNotNull();
                            Assertions
                            .assertThat(userLoginResponse.getEmail()).isEqualTo("jhonDoe@gmail.com");
                        });

    }


}
