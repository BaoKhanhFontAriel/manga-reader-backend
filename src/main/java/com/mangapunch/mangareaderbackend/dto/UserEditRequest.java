package com.mangapunch.mangareaderbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEditRequest {
    private String fullname;
    private String email;
    private String username;
}
