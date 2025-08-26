package com.authguard.authguard_user_service;


import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.authguard.authguard_user_service.model.entity.UserEntity;
import com.authguard.authguard_user_service.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("default") 
public class UserRepoTest {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    void contextLoads() {

    }

    void createUser() throws Exception {
        try{
        UserEntity user = UserEntity.builder()
                .contactNumber("8369517140")
                .firstName("ruddarm")
                .lastName("Mourya")
                .email("ruddarmmaurya@gmail.com")
                .hashPassword(passwordEncoder.encode("ruddarm4234")).build();
        user = userRepo.save(user);
        }catch(DataIntegrityViolationException ex){
            throw ex;
        }
        System.out.println("Saved User Entity");
    }
    @Test
    void createUserWithExistingEmailTest(){
        assertThatThrownBy(()-> createUser())
            .isInstanceOf(DataIntegrityViolationException.class);

    }

}
