package com.mangapunch.mangareaderbackend.controllers;

import com.mangapunch.mangareaderbackend.dto.ApiResponse;
import com.mangapunch.mangareaderbackend.dto.JwtAuthenticationResponse;
import com.mangapunch.mangareaderbackend.dto.LoginRequest;
import com.mangapunch.mangareaderbackend.dto.SignupRequest;
import com.mangapunch.mangareaderbackend.exceptions.AppException;
import com.mangapunch.mangareaderbackend.models.Role;
import com.mangapunch.mangareaderbackend.models.RoleEnum;
import com.mangapunch.mangareaderbackend.models.User;
import com.mangapunch.mangareaderbackend.repositories.RoleRepository;
import com.mangapunch.mangareaderbackend.security.UserPrincipal;
import com.mangapunch.mangareaderbackend.service.UserService;
import com.mangapunch.mangareaderbackend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenUtil tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
            String jwt = tokenProvider.generateToken(authentication);

            JwtAuthenticationResponse response = JwtAuthenticationResponse.builder()
                    .id(user.getUser().getId())
                    .accessToken(jwt)
                    .tokenType("Bearer")
                    .username(user.getUser().getUsername())
                    .password(user.getUser().getPassword())
                    .build();

            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(response);

        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tên đăng nhập hoặc mật khẩu sai!", e);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {

        try {

            // check whether email or password existed
            if (userService.findByEmail(signUpRequest.getEmail()) != null) {
                throw new Exception("Email đã tồn tại!");
            }
            if (userService.findByUsername(signUpRequest.getUsername()) != null) {
                throw new Exception("Tên đăng nhập đã tồn tại!");
            }

            // Creating user's account
            User user = new User();
            user.setName(signUpRequest.getFullname());
            user.setEmail(signUpRequest.getEmail());
            user.setUsername(signUpRequest.getUsername());
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER);
            if (userRole == null)
                throw new Exception("User Role not set.");
            user.setRole(userRole);

            userService.addUser(user);

            // String path = "/api/users/" + user.getId();

            // URI location = ServletUriComponentsBuilder
            // .fromCurrentContextPath().path(path)
            // .buildAndExpand(user.getUsername()).toUri();

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}
