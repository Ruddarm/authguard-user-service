package com.authguard.authguard_user_service.ServiceTest;

import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.authguard.authguard_user_service.Exception.ResourceException;
import com.authguard.authguard_user_service.config.TestContainerConfig;
import com.authguard.authguard_user_service.dtos.UserLoginResponse;
import com.authguard.authguard_user_service.dtos.UserSingupRequest;
import com.authguard.authguard_user_service.model.entity.UserEntity;
import com.authguard.authguard_user_service.repository.UserRepository;
import com.authguard.authguard_user_service.service.UserService;

@ActiveProfiles("default")
@Import(TestContainerConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private ModelMapper modelMapper;

    @Spy
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testUserLoginResponse_whenEmailNotExist_thenReturnUserLoginResponse() throws ResourceException {

        UserSingupRequest singUpRequest = UserSingupRequest.builder().firstName("ruddarm")
                .lastName("mourya")
                .email("ruddarmmaurya@gmail.com")
                .password("securedpswd")
                .build();
        UserEntity stubedUserEntity = modelMapper.map(singUpRequest, UserEntity.class);
        when(userRepository.save(stubedUserEntity))
                .thenReturn(stubedUserEntity);
        UserLoginResponse createdUser = userService.createUser(singUpRequest);
        Assertions.assertThat(createdUser)
                .isNotNull()
                .isInstanceOf(UserLoginResponse.class);
        Assertions.assertThat(createdUser.getEmail())
                .isEqualTo(singUpRequest.getEmail());
    }
    

}
