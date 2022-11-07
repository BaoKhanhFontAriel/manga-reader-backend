package com.mangapunch.mangareaderbackend.controllers;

import com.mangapunch.mangareaderbackend.models.User;
import com.mangapunch.mangareaderbackend.repositories.UserRepository;
import com.mangapunch.mangareaderbackend.security.UserPrincipal;
import com.mangapunch.mangareaderbackend.service.S3BucketStorageService;
import com.mangapunch.mangareaderbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
@RequestMapping("/api/upload")
public class ImageUploaderController {
    @Autowired
    private S3BucketStorageService service;

    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @PostMapping(value = "/image", produces = { MediaType.IMAGE_PNG_VALUE, "application/json" })
    public ResponseEntity<?> uploadImage(@RequestParam("imageFile") MultipartFile file, Authentication auth) {
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        User currentUser = userPrincipal.getUser();
        String fileName = file.getOriginalFilename();

        try {
            if (currentUser != null) {
                String message = service.uploadFile(fileName, file);
                currentUser.setAvatar(service.getAvatarUrl(fileName));
                em.merge(currentUser);
                em.flush();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } else {
                throw new Exception("Không tồn tại user!");
            }

        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}
