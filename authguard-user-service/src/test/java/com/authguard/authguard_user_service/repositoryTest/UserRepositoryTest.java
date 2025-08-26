package com.authguard.authguard_user_service.repositoryTest;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.authguard.authguard_user_service.config.TestContainerConfig;
import com.authguard.authguard_user_service.model.entity.UserEntity;
import com.authguard.authguard_user_service.repository.UserRepository;
@Import(TestContainerConfig.class)
@DataJpaTest
@ActiveProfiles("default")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private UserEntity userEntity;

    @BeforeEach
    public void setUp() {
        userEntity = UserEntity.builder()
                .firstName("ruddarm")
                .lastName("mourya")
                .email("ruddarm4234@gmail.com")
                .hashPassword("ruddarm123")
                .build();
    }

    @Test
    public void testFindByEmail_whenEmailIsValid_thenReturnEmployee() {
        userRepository.save(userEntity);
        Optional<UserEntity> user = userRepository.findByEmail("ruddarm4234@gmail.com");
        Assertions.assertThat(user).isPresent();
        Assertions.assertThat(user.get().getEmail()).isEqualTo("ruddarm4234@gmail.com");
    }

    @Test
    public void testFindByEmail_whenEmailIsNotValid_thenReturnEmpty() {
        Optional<UserEntity> user = userRepository.findByEmail("notpresent@gamil.com");
        Assertions.assertThat(user).isEmpty();
    }
}
