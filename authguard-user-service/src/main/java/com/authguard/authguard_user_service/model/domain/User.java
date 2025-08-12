package com.authguard.authguard_user_service.model.domain;

import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails, Serializable {
    private String firstName;
    private String lastName;
    private String username;
    private String userId;
    private String password;
    private String email;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
