package com.dragon.bankingSystem.service;

import com.dragon.bankingSystem.entity.UserTransaction;
import com.dragon.bankingSystem.model.TransactionDto;
import com.dragon.bankingSystem.repository.TransactionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService{

    //inject the transaction repo
    TransactionRepo transactionRepo;
    ModelMapper modelMapper;
    @Autowired
    TransactionServiceImpl(TransactionRepo transactionRepo,ModelMapper modelMapper){
        this.transactionRepo = transactionRepo;
        this.modelMapper = modelMapper;
    }
    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        UserTransaction transactionEntity = modelMapper.map(transactionDto, UserTransaction.class);
        transactionRepo.save(transactionEntity);
    }
}
