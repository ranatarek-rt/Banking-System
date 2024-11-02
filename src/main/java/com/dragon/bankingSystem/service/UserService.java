package com.dragon.bankingSystem.service;

import com.dragon.bankingSystem.entity.User;
import com.dragon.bankingSystem.model.AccountRequest;
import com.dragon.bankingSystem.model.BankResponse;
import com.dragon.bankingSystem.model.CreditDebitRequest;
import com.dragon.bankingSystem.model.UserDto;

public interface UserService {
    BankResponse createUserAccount(UserDto userDto);
    BankResponse findUserByAccountNumber(AccountRequest accountRequest);
    String findUserNameByAccountNumber(AccountRequest accountRequest);
    BankResponse creditAccount(CreditDebitRequest creditDebitRequest);
    BankResponse debitAccount(CreditDebitRequest creditDebitRequest);
}
