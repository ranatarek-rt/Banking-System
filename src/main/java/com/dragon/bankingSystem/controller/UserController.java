package com.dragon.bankingSystem.controller;

import com.dragon.bankingSystem.model.BankResponse;
import com.dragon.bankingSystem.model.UserDto;
import com.dragon.bankingSystem.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UserController {
    UserService userService;
    //inject the user service
    UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/userAccount")
    public BankResponse addUserAccount(@RequestBody UserDto userDto){
        return userService.createUserAccount(userDto);
    }
}
