package com.dragon.bankingSystem.controller;

import com.dragon.bankingSystem.model.AccountRequest;
import com.dragon.bankingSystem.model.BankResponse;
import com.dragon.bankingSystem.model.UserDto;
import com.dragon.bankingSystem.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    UserService userService;
    //inject the user service
    UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public BankResponse addUserAccount(@RequestBody UserDto userDto){
        return userService.createUserAccount(userDto);
    }

    @GetMapping("/balance")
    public BankResponse userByAccountNumber(@RequestBody AccountRequest accountRequest){
        return userService.findUserByAccountNumber(accountRequest);
    }

    @GetMapping("/userName")
    public String userNameByAccountNumber(@RequestBody AccountRequest accountRequest){
        return userService.findUserNameByAccountNumber(accountRequest);
    }
}
