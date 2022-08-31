package com.mangapunch.mangareaderbackend.dto;

import com.mangapunch.mangareaderbackend.models.User;
import com.mangapunch.mangareaderbackend.security.UserPrincipal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String username;
    private String password;
    
    public JwtAuthenticationResponse(String accessToken, String username, String password) {
        this.accessToken = accessToken;
        this.username = username;
        this.password = password;
    }
}
