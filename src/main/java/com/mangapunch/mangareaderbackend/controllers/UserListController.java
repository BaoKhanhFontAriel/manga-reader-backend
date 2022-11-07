package com.mangapunch.mangareaderbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mangapunch.mangareaderbackend.dto.PasswordRequest;
import com.mangapunch.mangareaderbackend.models.User;
import com.mangapunch.mangareaderbackend.service.UserService;

@RequestMapping("/api/users")
@RestController
public class UserListController {
    @Autowired
    private UserService userService;

    // get all Users
    @GetMapping("")
    public String listAllUser(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/user")
    public ResponseEntity<?> findUserByEmail(@RequestParam String email) {
        try {
            User user = userService.findByEmail(email);
            if (user != null) {

                return ResponseEntity.status(HttpStatus.OK).body(user);
            } else {
                throw new Exception("User không tồn tại!");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping("/{userid}/change-password")
    public ResponseEntity<?> changePasswordFromEmail(@PathVariable long userid, @RequestBody PasswordRequest request) {
        try {
            User user = userService.findById(userid);
            userService.editPassword(user, request.getRawPassword());
            return ResponseEntity.status(HttpStatus.OK).body("Mật khẩu cập nhật thành công!");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mật khẩu cập nhật không thành công!", e);
        }
    }
}
