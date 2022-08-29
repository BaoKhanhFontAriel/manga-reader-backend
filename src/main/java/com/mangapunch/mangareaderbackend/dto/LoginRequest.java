package com.mangapunch.mangareaderbackend.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "{name.notblank}")
    private String username;
    @NotBlank(message = "{name.notblank}")
    private String password;
}
