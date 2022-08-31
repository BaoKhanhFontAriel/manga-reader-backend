package com.mangapunch.mangareaderbackend.controllers;

import com.mangapunch.mangareaderbackend.dto.SignupRequest;
import com.mangapunch.mangareaderbackend.models.Manga;
import com.mangapunch.mangareaderbackend.models.User;
import com.mangapunch.mangareaderbackend.security.UserPrincipal;
import com.mangapunch.mangareaderbackend.service.MangaService;
import com.mangapunch.mangareaderbackend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MangaService mangaService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // get all Users
    @GetMapping("")
    public String listAllUser(Model model) {
        model.addAttribute("users", userService.getAllUsers());

        return "users";
    }

    // get User detail
    @GetMapping("/{id}")
    public String showUserDetail(@PathVariable("id") Long UserId) {
        // model.addAttribute("User", userService.getUserByid(UserId));
        return "User";
    }

    // update detail of existed user
    @PutMapping(value = "/edit/{id}", consumes = { "application/json" })
    public User editUser(@RequestBody SignupRequest updatedData, @RequestParam("id") Long userId) {
        User updatedUser = modelMapper.map(updatedData, User.class);
        if (updatedUser != null) {
            userService.updateUser(userId, updatedUser);
        }
        return userService.findById(userId);
    }

    // delete existed user
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long UserId) {
        Optional<User> existedUser = userService.getUserByid(UserId);
        existedUser.ifPresent((editUser) -> {
            userService.deleteUser(editUser);
        });
    }

    @GetMapping("/add-favorite")
    public List<Manga> addMangaToFavorite(@RequestParam String username, @RequestParam long mangaid) {
        return userService.addMangaToFavorite(mangaid, username);
    }

    @GetMapping("/remove-favorite")
    public List<Manga> removeMangaToFavorite(@RequestParam String username, @RequestParam long mangaid) {
        return userService.removeMangaToFavorite(mangaid, username);
    }

    // check if the manga is favorited by user
    @GetMapping("/is-favorited")
    public boolean isMangaFavoritedByUser(@RequestParam("mangaid") long mangaid,
            @RequestParam("username") String username) {
        return userService.isMangaFavoritedByUser(mangaid, username);
    };

    // @PreAuthorize("#username == principal.username")
    @GetMapping("/{username}/favorites")
    public ResponseEntity<?> getFavoriteMangaByUserId(@PathVariable("username") String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getFavoriteMangaByUsername(username));
    };
}
