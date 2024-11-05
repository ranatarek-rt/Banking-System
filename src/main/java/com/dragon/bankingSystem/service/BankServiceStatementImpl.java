package com.dragon.bankingSystem.service;

import com.dragon.bankingSystem.entity.UserTransaction;
import com.dragon.bankingSystem.model.BankStatementDto;
import com.dragon.bankingSystem.model.BankTransactionResponse;
import com.dragon.bankingSystem.repository.TransactionRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class BankServiceStatementImpl implements BankServiceStatement{

    TransactionRepo transactionRepo;

    // inject the repo
    @Autowired
    BankServiceStatementImpl(TransactionRepo transactionRepo){
        this.transactionRepo = transactionRepo;
    }

    /*

    Retrieve List of user Transactions within certain date given an account number
    generate a pdf for the user
    we can create a button in the front end to download that pdf upon requiring it
    or send the pdf to the email, or we can do both options

    */

    public BankTransactionResponse findUserTransactionList(BankStatementDto bankStatementDto) {
        //parse the string to local date
        LocalDate startDate = LocalDate.parse(bankStatementDto.getStartDate(), DateTimeFormatter.ISO_DATE);
        LocalDate endDate = LocalDate.parse(bankStatementDto.getEndDate(), DateTimeFormatter.ISO_DATE);

        //retrieve the user transaction based on the source account number
        List<UserTransaction> transactions = transactionRepo.findBySourceAccountNumber(bankStatementDto.getAccountNumber());

        /*
        since the createdAt and modifiedAt is stored as Local Time And Date
        we need to convert that to local Date , we are comparing the date only not the time
        */
        List<UserTransaction> filteredTransactions = transactions.stream()
                .filter(transaction -> {
                    LocalDate transactionDate = transaction.getCreatedAt().toLocalDate();
                    return !transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate);
                })
                .toList();

        if (filteredTransactions.isEmpty()) {
            return new BankTransactionResponse("404", "No transactions found for the specified account and date range", null);
        }

        return new BankTransactionResponse("200", "Transactions found", filteredTransactions);
    }



}
