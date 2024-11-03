package com.dragon.bankingSystem.service;

import com.dragon.bankingSystem.model.BankStatementDto;
import com.dragon.bankingSystem.model.BankTransactionResponse;

public interface BankServiceStatement {


    BankTransactionResponse findUserTransactionList(BankStatementDto bankStatementDto);
}
