package com.mangapunch.mangareaderbackend.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank(message = "{name.notblank}")
    private String fullname;
    @NotBlank(message = "{name.notblank}")
    private String email;
    @NotBlank(message = "{name.notblank}")
    private String username;
    @NotBlank(message = "{name.notblank}")
    private String password;
}