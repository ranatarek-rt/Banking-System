package com.dragon.bankingSystem.controller;


import com.dragon.bankingSystem.model.BankStatementDto;
import com.dragon.bankingSystem.model.BankTransactionResponse;
import com.dragon.bankingSystem.service.BankServiceStatement;
import com.dragon.bankingSystem.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    TransactionService transactionService;
    BankServiceStatement bankServiceStatement;
    TransactionController(TransactionService transactionService,BankServiceStatement bankServiceStatement){
        this.transactionService = transactionService;
        this.bankServiceStatement = bankServiceStatement;
    }

    @Operation(summary = "get all user transaction filtered by date",
            description = "Retrieve all user transactions")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200", description = "Transactions found "),
            @ApiResponse(responseCode = "500", description = "No transactions found for the specified account and date range")
    })
    @PostMapping("/allTransactions")
    public BankTransactionResponse findAllTransactions(@RequestBody BankStatementDto bankStatementDto){

        return bankServiceStatement.findUserTransactionList(bankStatementDto);
    }

}
