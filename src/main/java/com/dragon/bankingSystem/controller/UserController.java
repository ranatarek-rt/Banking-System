package com.dragon.bankingSystem.controller;

import com.dragon.bankingSystem.entity.User;
import com.dragon.bankingSystem.model.*;
import com.dragon.bankingSystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
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
    public String userLogin(@ModelAttribute UserDto userDto,Model model){
        String token =  userService.verifyUser(userDto);
        if(token.equals("failure")){
            model.addAttribute("loginError", "Invalid username or password");
            System.out.println("error");
            return "redirect:/api/user/showLoginForm";

        }
        else{
            System.out.println(token);
            return "redirect:/api/user/success";
        }

    }

    @GetMapping("/error")
    public String errorPage(){
        return "error";
    }
    @GetMapping("/success")
    public String successPage(){
        return "success";
    }

    @GetMapping("/showLoginForm")
    public String showForm(@RequestParam(value = "error", required = false)String error ,Model model){
        if (error != null) {
            model.addAttribute("loginError", "Invalid username or password.");
        }
        model.addAttribute("user",new UserDto());
        return "loginForm";
    }












    @Operation(summary = "Create a user account",
            description = "Adds a new user account to the banking system.")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200", description = "User account created successfully"),
            @ApiResponse(responseCode = "500", description = "Failed to create user account"),
            @ApiResponse(responseCode = "409", description = "Email is already registered.")
    })
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
    @PostMapping("/transferMoney")
    public BankResponse transferMoneyBetweenUsers(@RequestBody TransferMoneyRequest transferMoneyRequest){
        return userService.transferMoney(transferMoneyRequest);
    }


}
