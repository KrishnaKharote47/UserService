package com.user.controller;

import com.user.dto.LoginRequstDto;
import com.user.dto.SingUpRequstDto;
import com.user.dto.UserDto;
import com.user.models.Token;
import com.user.models.User;
import com.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("signup")
    public UserDto singUp(@RequestBody SingUpRequstDto singUpRequstDto) {

        User user = userService.singUp(singUpRequstDto.getEmail(), singUpRequstDto.getName(), singUpRequstDto.getPassword());

        return UserDto.from(user);
    }


    @PostMapping("login")
    public Token login(@RequestBody LoginRequstDto requstDto) {

        Token token = userService.login(requstDto.getEmail(), requstDto.getPassword());

        return token;
    }

    @PostMapping("validate/{tokenValue}")
    public UserDto validate(@PathVariable("tokenValue") String tokenValue) {
        try {
            return UserDto.from(userService.validatToken(tokenValue));
        } catch (Exception e) {
            return null;
        }
    }

}
