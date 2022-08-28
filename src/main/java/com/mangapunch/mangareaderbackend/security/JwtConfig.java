package com.mangapunch.mangareaderbackend.security;


import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Configuration
    public class JwtConfig {
        @Bean
        public SecretKey jwtSecretKey() {
            return Keys.secretKeyFor(SignatureAlgorithm.HS512);
        }
    }


