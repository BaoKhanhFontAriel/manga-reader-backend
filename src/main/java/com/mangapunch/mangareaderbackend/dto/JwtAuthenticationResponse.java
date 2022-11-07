package com.mangapunch.mangareaderbackend.dto;

import com.mangapunch.mangareaderbackend.models.User;
import com.mangapunch.mangareaderbackend.security.UserPrincipal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtAuthenticationResponse {
    private String accessToken;

    private String tokenType;
    private String username;
    private String password;
    private long id;
}
