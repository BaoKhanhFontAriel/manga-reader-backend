package com.mangapunch.mangareaderbackend.controllers;

import com.mangapunch.mangareaderbackend.dto.SignupRequest;
import com.mangapunch.mangareaderbackend.models.User;
import com.mangapunch.mangareaderbackend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/api/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    // get all Users
    @GetMapping("/")
    public String listAllUser(Model model) {
        model.addAttribute("users", userService.getAllUsers());

        return "users";
    }

    // get User detail
    @GetMapping("/{id}")
    public String showUserDetail(Model model, @PathVariable("id") Long UserId) {
        model.addAttribute("User", userService.getUserByid(UserId));
        return "User";
    }

    // add new User
    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("UserForm", new SignupRequest());
        return "add-User";
    }

    // Xử lý request "/Users" có method POST
    @PostMapping(value = "/add", consumes = { "multipart/form-data" })
    public String addUser(@Valid @ModelAttribute("UserForm") SignupRequest UserRequest, BindingResult bindingResult,
            Model model) {
        User User = modelMapper.map(UserRequest, User.class);
        userService.addUser(User);
        return "redirect:/Users";

    }

    // Xử lý request "/Users/{id}" có method PUT
    @RequestMapping("/edit/{id}")
    public String editUser(@RequestParam("id") Long UserId, Model model) {
        Optional<User> UserEdit = userService.getUserByid(UserId);
        UserEdit.ifPresent(User -> model.addAttribute("User", UserEdit));
        return "edit-User";

    }

    // Xử lý request "/Users/{id}" có method DELETE
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long UserId) {
        userService.deleteUser(UserId);

        return "redirect:/Users";

    }
}
