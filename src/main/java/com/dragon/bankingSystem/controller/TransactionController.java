package com.dragon.bankingSystem.controller;


import com.dragon.bankingSystem.model.BankStatementDto;
import com.dragon.bankingSystem.model.BankTransactionResponse;
import com.dragon.bankingSystem.service.BankServiceStatement;
import com.dragon.bankingSystem.service.TransactionService;
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


    @PostMapping("/allTransactions")
    public BankTransactionResponse findAllTransactions(@RequestBody BankStatementDto bankStatementDto){

        return bankServiceStatement.findUserTransactionList(bankStatementDto);
    }

}
