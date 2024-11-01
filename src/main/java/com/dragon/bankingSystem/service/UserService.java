package com.dragon.bankingSystem.service;

import com.dragon.bankingSystem.model.BankResponse;
import com.dragon.bankingSystem.model.UserDto;

public interface UserService {
    BankResponse createUserAccount(UserDto userDto);
}
