package com.dragon.bankingSystem.controller;

import com.dragon.bankingSystem.model.AuthRequest;
import com.dragon.bankingSystem.model.BankResponse;
import com.dragon.bankingSystem.model.UserDto;
import com.dragon.bankingSystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/auth")
public class UserAuthController {
    UserService userService;
    //inject the user service
    UserAuthController(UserService userService){
        this.userService = userService;
    }


    @PostMapping("/login")
    public BankResponse authUser(@RequestBody AuthRequest authRequest){
        return userService.verifyUser(authRequest);
    }

    @Operation(summary = "Create a user account",
            description = "Adds a new user account to the banking system.(register user)")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200", description = "User account created successfully"),
            @ApiResponse(responseCode = "500", description = "Failed to create user account"),
            @ApiResponse(responseCode = "409", description = "Email is already registered.")
    })
    @PostMapping
    public BankResponse addUserAccount(@RequestBody UserDto userDto){

        return userService.createUserAccount(userDto);
    }
}
