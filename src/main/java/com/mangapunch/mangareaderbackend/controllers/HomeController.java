package com.mangapunch.mangareaderbackend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @GetMapping("/")
    public String showHomePage(HttpSession session, Model model) {
        return "index";
    }

    @GetMapping("/hello")
    public String home() {
        return "Hello Docker World";
    }
}
