package com.dragon.bankingSystem.service;



import com.dragon.bankingSystem.entity.User;
import com.dragon.bankingSystem.model.AccountInfo;
import com.dragon.bankingSystem.model.AccountRequest;
import com.dragon.bankingSystem.model.BankResponse;
import com.dragon.bankingSystem.model.UserDto;
import com.dragon.bankingSystem.repository.UserRepo;
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




}
