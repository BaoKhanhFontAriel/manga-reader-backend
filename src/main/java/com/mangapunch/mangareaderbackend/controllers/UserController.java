package com.mangapunch.mangareaderbackend.controllers;

import com.mangapunch.mangareaderbackend.dto.PasswordRequest;
import com.mangapunch.mangareaderbackend.dto.SignupRequest;
import com.mangapunch.mangareaderbackend.dto.UserEditRequest;
import com.mangapunch.mangareaderbackend.models.Chapter;
import com.mangapunch.mangareaderbackend.models.Manga;
import com.mangapunch.mangareaderbackend.models.RoleEnum;
import com.mangapunch.mangareaderbackend.models.User;
import com.mangapunch.mangareaderbackend.security.UserPrincipal;
import com.mangapunch.mangareaderbackend.service.ChapterService;
import com.mangapunch.mangareaderbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChapterService chapterService;

    @GetMapping("")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<?> getUserDetail(Authentication auth) {
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        User currentUser = userPrincipal.getUser();
        try {
            if (currentUser != null) {
                return ResponseEntity.status(HttpStatus.OK).body(currentUser);
            } else {
                throw new Exception("Không tồn tại user!");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    // update detail of existed user
    @RolesAllowed("ROLE_USER")
    @PutMapping(value = "/edit", consumes = { "application/json" })
    public ResponseEntity<?> editUser(@RequestBody UserEditRequest updatedData, Authentication auth) {

        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

        User currentUser = userPrincipal.getUser();
        try {
            User user = userService.editUser(currentUser, updatedData);

            return ResponseEntity.status(HttpStatus.OK).body("Cập nhật thành công!");

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    // xử lý request người dùng yêu cầu xóa tài khoản
    @RolesAllowed("ROLE_USER")
    @DeleteMapping("/delete")
    public void deleteUser() {
        // Optional<User> existedUser = userService.getUserByid(UserId);
        // existedUser.ifPresent((editUser) -> {
        // userService.deleteUser(editUser);
        // });
    }

    @GetMapping("/add-favorite")
    @RolesAllowed("ROLE_USER")
    public List<Manga> addMangaToFavorite(@RequestParam long mangaid, Authentication auth) {
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        User currentUser = userPrincipal.getUser();
        return userService.addMangaToFavorite(mangaid, currentUser);
    }

    @GetMapping("/remove-favorite")
    @RolesAllowed("ROLE_USER")
    public List<Manga> removeMangaToFavorite(@RequestParam long mangaid, Authentication auth) {
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        User currentUser = userPrincipal.getUser();
        return userService.removeMangaToFavorite(mangaid, currentUser);
    }

    // check if the manga is favorited by user
    @RolesAllowed("ROLE_USER")
    @GetMapping("/is-favorited")
    public boolean isMangaFavoritedByUser(@RequestParam("mangaid") long mangaid, Authentication auth) {
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        User currentUser = userPrincipal.getUser();
        return userService.isMangaFavoritedByUser(mangaid, currentUser);
    };

    @RolesAllowed("ROLE_USER")
    @GetMapping("/favorites")
    public ResponseEntity<?> getFavoriteManga(Authentication auth) {
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        User currentUser = userPrincipal.getUser();
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getFavoriteMangaByUserId(currentUser));
    };

    @GetMapping("/upload-chapters")
    @RolesAllowed("ROLE_USER")
    public List<Chapter> getAllUploadedChapter(Authentication auth) {
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        User currentUser = userPrincipal.getUser();
        return chapterService.getAllUploadedChapters(currentUser);
    };

    @RolesAllowed("ROLE_USER")
    @PostMapping("/check-password")
    public boolean isPasswordMatch(@RequestBody PasswordRequest request, Authentication auth) {
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        User currentUser = userPrincipal.getUser();
        return userService.isPasswordMatch(currentUser, request.getRawPassword());
    }

    @RolesAllowed("ROLE_USER")
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordRequest request, Authentication auth) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
            User currentUser = userPrincipal.getUser();
            userService.editPassword(currentUser, request.getRawPassword());
            return ResponseEntity.status(HttpStatus.OK).body("Mật khẩu cập nhật thành công!");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mật khẩu cập nhật không thành công!", e);
        }
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping("/is-admin")
    public ResponseEntity<?> isAdmin(Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(
                a -> a.getAuthority().equals(RoleEnum.ROLE_ADMIN.getValue()));
        return ResponseEntity.status(HttpStatus.OK).body(isAdmin);
    }
}
