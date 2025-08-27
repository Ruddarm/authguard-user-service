package com.authguard.authguard_user_service.ServiceTest;

import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.authguard.authguard_user_service.Exception.ResourceException;
import com.authguard.authguard_user_service.dtos.UserLoginResponse;
import com.authguard.authguard_user_service.dtos.UserSingupRequest;
import com.authguard.authguard_user_service.model.domain.User;
import com.authguard.authguard_user_service.model.entity.UserEntity;
import com.authguard.authguard_user_service.repository.UserRepository;
import com.authguard.authguard_user_service.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

// @ActiveProfiles("default")
// // @Import(TestContainerConfig.class)
// // @AutoConfigureTestDatabase(replace =
// AutoConfigureTestDatabase.Replace.NONE)
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
        public void testCreateUser_whenEmailNotExist_thenReturnUserLoginResponse() throws ResourceException {

                UserSingupRequest singUpRequest = UserSingupRequest.builder().firstName("jhon")
                                .lastName("jhon")
                                .email("jhonDeo@gmail.com")
                                .password("securedpswd")
                                .build();
                UserEntity stubedUserEntity = modelMapper.map(singUpRequest, UserEntity.class);
                when(userRepository.save(any(UserEntity.class)))
                                .thenReturn(stubedUserEntity);
                UserLoginResponse createdUser = userService.createUser(singUpRequest);
                Assertions.assertThat(createdUser)
                                .isNotNull()
                                .isInstanceOf(UserLoginResponse.class);
                Assertions.assertThat(createdUser.getEmail())
                                .isEqualTo(singUpRequest.getEmail());
        }

        @Test
        public void testCreateUser_whenEmailExist_thenThrowEmailAlreadyExistError() throws ResourceException {
                UserSingupRequest singUpRequest = UserSingupRequest.builder().firstName("ruddarm")
                                .lastName("mourya")
                                .email("ruddarmmaurya@gmail.com")
                                .password("securedpswd")
                                .build();
                UserEntity stubedUserEntity = modelMapper.map(singUpRequest, UserEntity.class);
                when(userRepository.existsByEmail(anyString()))
                                .thenReturn(true);
                Assertions.assertThatThrownBy(() -> userService.createUser(singUpRequest))
                                .isInstanceOf(ResourceException.class)
                                .hasMessage("email alredy exist");
        }

        @Test
        public void testLoadUserByID_whenIdisValid_returnUser() throws JsonProcessingException, ResourceException {
                // UserEntity
                UUID userId = UUID.randomUUID();
                UserEntity stubedUser = UserEntity.builder()
                                .firstName("jhon")
                                .lastName("doe")
                                .userId(userId)
                                .build();
                when(userRepository.findById(userId))
                                .thenReturn(Optional.of(stubedUser));
                Assertions.assertThat(userService.loadByUserId(userId))
                                .isNotNull()
                                .isInstanceOf(User.class);
        }

        @Test
        public void testLoadUserByUsername_whenUsernameIsValid() {
                String username = "testing@gmail.com";

                UserEntity userEntity = UserEntity
                                .builder()
                                .userId(UUID.randomUUID())
                                .email(username)
                                .hashPassword("securedPaswd")
                                .build();
                when(userRepository.findByEmail(username))
                                .thenReturn(Optional.of(userEntity));
                UserDetails userdetails = userService.loadUserByUsername(username);
                Assertions
                                .assertThat(userdetails)
                                .isNotNull()
                                .isInstanceOf(UserDetails.class);
                Assertions
                                .assertThat(userdetails.getUsername())
                                .isEqualTo(username);
                Assertions
                                .assertThat(userdetails.getPassword())
                                .isEqualTo(userEntity.getHashPassword());

        }

}
