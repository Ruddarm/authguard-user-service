package com.authguard.authguard_user_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.authguard.authguard_user_service.model.entity.UserEntity;
import com.authguard.authguard_user_service.repository.UserRepository;

import io.jsonwebtoken.security.Password;

@SpringBootTest
public class UserRepoTest {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {

    }

    // to test user creation. 

    @Test
    void testCreateUser() {
        UserEntity user = UserEntity.builder()
                .contactNumber("8369517140")
                .firstName("ruddarm")
                .lastName("Mourya")
                .email("ruddarmmaurya@gmail.com")
                .hashPassword(passwordEncoder.encode("ruddarm4234")).build();
        user = userRepo.save(user);
        System.out.println("Saved User Entity");
    }

}
