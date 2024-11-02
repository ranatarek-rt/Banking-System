package com.dragon.bankingSystem.service;



import com.dragon.bankingSystem.entity.User;
import com.dragon.bankingSystem.model.*;
import com.dragon.bankingSystem.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.dragon.bankingSystem.utils.AccountUtil.generateAccountNumber;

@Service
public class UserServiceImpl implements UserService {

    //inject user repo
    UserRepo userRepo;

    ModelMapper modelMapper;

    EmailService emailService;
    @Autowired
    UserServiceImpl(UserRepo userRepo,ModelMapper modelMapper,EmailService emailService){
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
    }

//    create a new user account inside the bank and generate the new account number
//    and return back the account information to the user
    @Override
    public BankResponse createUserAccount(UserDto userDto) {
        //check by email if the user is already registered
        if(userRepo.existsByEmail(userDto.getEmail())){
            return new BankResponse("409","Email is already registered.",null);
        }
        User userEntity = modelMapper.map(userDto,User.class);
        userEntity.setAccountNumber(generateAccountNumber());
        //check for other error during saving the new user
        try{
            userRepo.save(userEntity);

            AccountInfo accountInfo = new AccountInfo(userEntity.getFullName(),
                    userEntity.getAccountNumber(), userEntity.getAccountBalance());
            //we can integrate any email service here
            return new BankResponse("201", "Account successfully created.", accountInfo);
        } catch (DataIntegrityViolationException e) {
            return new BankResponse("500", "Duplicate account number generated. Try again.", null);
        } catch (Exception e) {
            return new BankResponse("500", "Account creation failed: " + e.getMessage(), null);
        }

    }

    //return user using the account number
    @Override
    public BankResponse findUserByAccountNumber(AccountRequest accountRequest) {
        Optional<User> tempUser = userRepo.findByAccountNumber(accountRequest.getAccountNumber());
        if (tempUser.isPresent()) {
            User existingUser = tempUser.get();
            AccountInfo accountInfo = new AccountInfo(
                    existingUser.getFullName(),
                    existingUser.getAccountNumber(),
                    existingUser.getAccountBalance()
            );
            return new BankResponse("200", "User is fetched successfully", accountInfo);
        } else {
            return new BankResponse("500", "No user found with such account number", null);
        }
    }

    //return username by account number
    @Override
    public String findUserNameByAccountNumber(AccountRequest accountRequest) {
        return userRepo.findByAccountNumber(accountRequest.getAccountNumber()).
                map(user->user.getFullName()).orElse("No user Found with such account number");
    }

    //credit an account which mean to increase the balance of a user by certain amount
    @Override
    public BankResponse creditAccount(CreditDebitRequest creditDebitRequest) {
        Optional<User> tempUser = userRepo.findByAccountNumber(creditDebitRequest.getAccountNumber());
        if (tempUser.isPresent()) {
            User existingUser = tempUser.get();
            existingUser.setAccountBalance(existingUser.getAccountBalance().add(creditDebitRequest.getAmount()));
            AccountInfo accountInfo = new AccountInfo(
                    existingUser.getFullName(),
                    existingUser.getAccountNumber(),
                    existingUser.getAccountBalance()
            );
            //save the updates to the database
            userRepo.save(existingUser);
            return new BankResponse("200", "User is credited successfully", accountInfo);
        } else {
            return new BankResponse("500", "No user found with such account number", null);
        }

    }

    // debit an account which mean to decrease the balance of a user by certain amount
    @Override
    public BankResponse debitAccount(CreditDebitRequest creditDebitRequest) {
        Optional<User> tempUser = userRepo.findByAccountNumber(creditDebitRequest.getAccountNumber());
        // check if the user exist
        if (tempUser.isPresent()) {
            User existingUser = tempUser.get();
            // Check if the balance is sufficient for withdrawal
            if (existingUser.getAccountBalance().compareTo(creditDebitRequest.getAmount()) < 0) {
                return new BankResponse("400", "Insufficient balance for withdrawal", null);
            }
            // Update the account balance
            existingUser.setAccountBalance(existingUser.getAccountBalance().subtract(creditDebitRequest.getAmount()));

            AccountInfo accountInfo = new AccountInfo(
                    existingUser.getFullName(),
                    existingUser.getAccountNumber(),
                    existingUser.getAccountBalance()
            );
            //save the updates to the database
            userRepo.save(existingUser);
            return new BankResponse("200", "User debit account is saved successfully", accountInfo);
        } else {
            return new BankResponse("500", "No user found with such account number", null);
        }
    }




    @Override
    @Transactional
    public BankResponse transferMoney(TransferMoneyRequest transferMoneyRequest) {
        Optional<User> sourceUser = userRepo.findByAccountNumber(transferMoneyRequest.getSourceAccountNumber());
        Optional<User> destUser = userRepo.findByAccountNumber(transferMoneyRequest.getDestinationAccountNumber());

        //check for the existence of both users
        if(sourceUser.isPresent() && destUser.isPresent()){
            User sourceUserTemp = sourceUser.get();
            User destUserTemp = destUser.get();

            //check for sufficient funds in the user that wants to send the money
            if(sourceUserTemp.getAccountBalance()
                    .compareTo(transferMoneyRequest.getTransferAmount()) <0){
                return new BankResponse("400",
                        "Insufficient balance for withdrawal", null);
            }

            //check if both source and destination have the same account number
            if (sourceUserTemp.getAccountNumber().equals(destUserTemp.getAccountNumber())) {
                return new BankResponse("400",
                        "Cannot transfer money to the same account", null);
            }
            sourceUserTemp.setAccountBalance(sourceUserTemp.getAccountBalance()
                    .subtract(transferMoneyRequest.getTransferAmount()));
            destUserTemp.setAccountBalance(destUserTemp.getAccountBalance()
                    .add(transferMoneyRequest.getTransferAmount()));
            userRepo.save(sourceUserTemp);
            userRepo.save(destUserTemp);

            AccountInfo accountInfo = new AccountInfo(
                    sourceUserTemp.getFullName(),
                    sourceUserTemp.getAccountNumber(),
                    sourceUserTemp.getAccountBalance()
            );

            return new BankResponse("200",
                    "the money transfer is completed successfully", accountInfo);
        }
        else {
            return new BankResponse("500",
                    "No user found with such account number", null);
        }

    }



}
