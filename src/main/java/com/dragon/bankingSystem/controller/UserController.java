package com.dragon.bankingSystem.controller;

import com.dragon.bankingSystem.entity.User;
import com.dragon.bankingSystem.model.*;
import com.dragon.bankingSystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController {
    UserService userService;
    //inject the user service
    UserController(UserService userService){
        this.userService = userService;
    }



    @GetMapping
    public List<User> findAll(){
        return userService.findAllUsers();
    }

    @PostMapping("/login")
    public String userLogin(@RequestBody UserDto userDto){
        return userService.verifyUser(userDto);
    }

    @Operation(summary = "Create a user account",
            description = "Adds a new user account to the banking system.")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200", description = "User account created successfully"),
            @ApiResponse(responseCode = "500", description = "Failed to create user account"),
            @ApiResponse(responseCode = "409", description = "Email is already registered.")
    })
    @PostMapping
    public BankResponse addUserAccount(@RequestBody UserDto userDto){
        return userService.createUserAccount(userDto);
    }

    @Operation(summary = "Retrieve user balance",
            description = "Fetches the account balance for the specified account number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User fetched successfully"),
            @ApiResponse(responseCode = "500", description = "No user found with such account number")
    })
    @GetMapping("/balance")
    public BankResponse userByAccountNumber(@RequestBody AccountRequest accountRequest){
        return userService.findUserByAccountNumber(accountRequest);
    }

    @Operation(summary = "Retrieve user name by account number",
            description = "Gets the user name associated with the specified account number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User name fetched successfully"),
            @ApiResponse(responseCode = "500", description = "No user Found with such account number")
    })
    @GetMapping("/userName")
    public String userNameByAccountNumber(@RequestBody AccountRequest accountRequest){
        return userService.findUserNameByAccountNumber(accountRequest);
    }

    @Operation(summary = "Credit user account",
            description = "Credits a specified amount to the user's account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User account credited successfully"),
            @ApiResponse(responseCode = "500", description = "Failed to credit user account")
    })
    @PostMapping("/credit")
    public BankResponse userCreditAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return userService.creditAccount(creditDebitRequest);
    }

    @Operation(summary = "Debit user account",
            description = "Debits a specified amount from the user's account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User account debited successfully"),
            @ApiResponse(responseCode = "500", description = "Failed to debit user account or insufficient funds")
    })
    @PostMapping("/debit")
    public BankResponse userDebitAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return userService.debitAccount(creditDebitRequest);
    }

    @Operation(summary = "Transfer money between users",
            description = "Transfers a specified amount from one user to another.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Money transfer completed successfully"),
            @ApiResponse(responseCode = "500", description = "Transfer failed due to invalid accounts or insufficient funds")
    })
    @PostMapping("/transferMoney")
    public BankResponse transferMoneyBetweenUsers(@RequestBody TransferMoneyRequest transferMoneyRequest){
        return userService.transferMoney(transferMoneyRequest);
    }


}
