package com.authguard.authguard_user_service.config;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.authguard.authguard_user_service.service.UserService;

@Configuration
public class Config {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public DaoAuthenticationProvider userAuthProvider(UserService userService) {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider(userService);
        daoProvider.setPasswordEncoder(passwordEncoder());
        return daoProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserService clientService) {
        return new ProviderManager(List.of(userAuthProvider(clientService)));
    }

    // @Bean
    // public CacheManager redisChacheMangaer(RedisConnectionFactory
    // connectionFatory) {
    // Map<String, RedisCacheConfiguration> cacheMangaer = new HashMap<>();
    // cacheMangaer.put("logedInUser",
    // RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)).enableTimeToIdle());
    // return
    // RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFatory))
    // .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig())
    // .withInitialCacheConfigurations(cacheMangaer).build();
    // }

}
