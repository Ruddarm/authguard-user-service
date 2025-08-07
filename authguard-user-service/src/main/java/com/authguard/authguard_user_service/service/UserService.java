package com.authguard.authguard_user_service.service;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authguard.authguard_user_service.Exception.ResourceException;
import com.authguard.authguard_user_service.dtos.UserLoginResponse;
import com.authguard.authguard_user_service.dtos.UserSingupRequest;
import com.authguard.authguard_user_service.model.domain.User;
import com.authguard.authguard_user_service.model.entity.UserEntity;
import com.authguard.authguard_user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserLoginResponse createUser(UserSingupRequest singupRequest) throws ResourceException {
        if (userRepository.existsByEmail(singupRequest.getEmail())) {
            throw new ResourceException("email already exist");
        }
        UserEntity user = modelMapper.map(singupRequest, UserEntity.class);
        user.setHashPassword(passwordEncoder.encode(singupRequest.getPassword()));
        user = userRepository.save(user);
        return modelMapper.map(user, UserLoginResponse.class);
    }

    @Cacheable(cacheNames = "logedInUser", key = "#userId")
    public User loadByUserId(UUID userId) throws ResourceException {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceException("User not found for id : " + userId));
        return User.builder().username(user.getEmail()).userId(user.getUserId().toString())
                .firstName(user.getFirstName()).lastName(user.getLastName()).build();

    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found : " + username));
        return User.builder().username(username).userId(userEntity.getUserId().toString())
                .password(userEntity.getHashPassword()).build();
    }

}
