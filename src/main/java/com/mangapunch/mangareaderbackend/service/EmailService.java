package com.mangapunch.mangareaderbackend.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    String sendPasswordChangeEmail(String to);
}
