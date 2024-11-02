package com.dragon.bankingSystem.controller;

import com.dragon.bankingSystem.model.*;
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
    @PostMapping("/credit")
    public BankResponse userCreditAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return userService.creditAccount(creditDebitRequest);
    }
    @PostMapping("/debit")
    public BankResponse userDebitAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return userService.debitAccount(creditDebitRequest);
    }

    @PostMapping("/transferMoney")
    public BankResponse transferMoneyBetweenUsers(@RequestBody TransferMoneyRequest transferMoneyRequest){
        return userService.transferMoney(transferMoneyRequest);
    }
}
