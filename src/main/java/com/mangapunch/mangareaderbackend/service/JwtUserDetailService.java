package com.mangapunch.mangareaderbackend.service;

import com.mangapunch.mangareaderbackend.config.CustomUserDetails;
import com.mangapunch.mangareaderbackend.models.User;
import com.mangapunch.mangareaderbackend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailService implements UserDetailsService {

    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail);
        if (user != null){
            return new CustomUserDetails(user);
        } else {
            throw new UsernameNotFoundException("User get email or username " + usernameOrEmail + " does not exist.");
        }
    }
}
