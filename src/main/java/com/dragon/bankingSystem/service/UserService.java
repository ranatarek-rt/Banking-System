package com.dragon.bankingSystem.service;

import com.dragon.bankingSystem.entity.User;
import com.dragon.bankingSystem.model.*;

import java.util.List;

public interface UserService {
    BankResponse createUserAccount(UserDto userDto);
    BankResponse findUserByAccountNumber(AccountRequest accountRequest);
    String findUserNameByAccountNumber(AccountRequest accountRequest);
    BankResponse creditAccount(CreditDebitRequest creditDebitRequest);
    BankResponse debitAccount(CreditDebitRequest creditDebitRequest);
    BankResponse transferMoney(TransferMoneyRequest transferMoneyRequest);
    String verifyUser(UserDto userDto);
    List<User> findAllUsers();
}
