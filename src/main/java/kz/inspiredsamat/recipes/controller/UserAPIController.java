package kz.inspiredsamat.recipes.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kz.inspiredsamat.recipes.model.User;
import kz.inspiredsamat.recipes.service.UserService;

import javax.validation.Valid;

@RestController
public class UserAPIController {
    private final UserService userService;

    public UserAPIController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/register")
    public void register(@Valid @RequestBody User user) {
        userService.save(user);
    }
}
