package com.example.demo.insectcatalog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.insectcatalog.services.UserService;

@Controller // Spring MVCのコントローラとして動作
@RequestMapping("/user") // このコントローラの全てのマッピングに "/user" を追加
public class UserController {

    private final UserService userService;
    // UserServiceをUserControllerに注入
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ログインページを表示
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 登録ページを表示
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    // ユーザーの登録
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        userService.registerNewUser(username, password);
        return "redirect:/user/login";
    }

}