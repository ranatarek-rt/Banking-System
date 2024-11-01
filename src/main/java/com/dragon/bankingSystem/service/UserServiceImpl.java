package com.dragon.bankingSystem.service;


import com.dragon.bankingSystem.entity.User;
import com.dragon.bankingSystem.model.AccountInfo;
import com.dragon.bankingSystem.model.BankResponse;
import com.dragon.bankingSystem.model.UserDto;
import com.dragon.bankingSystem.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import static com.dragon.bankingSystem.utils.AccountUtil.generateAccountNumber;

@Service
public class UserServiceImpl implements UserService {

    //inject user repo
    UserRepo userRepo;

    ModelMapper modelMapper;

    @Autowired
    UserServiceImpl(UserRepo userRepo,ModelMapper modelMapper){
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
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

            AccountInfo accountInfo = new AccountInfo(userEntity.getFirstName()+
                    " "+userEntity.getLastName()+" "+ userEntity.getOtherName(),
                    userEntity.getAccountNumber(), userEntity.getAccountBalance());

            return new BankResponse("201", "Account successfully created.", accountInfo);
        } catch (DataIntegrityViolationException e) {
            return new BankResponse("500", "Duplicate account number generated. Try again.", null);
        } catch (Exception e) {
            return new BankResponse("500", "Account creation failed: " + e.getMessage(), null);
        }



    }
}
