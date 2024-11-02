package com.dragon.bankingSystem.service;

import com.dragon.bankingSystem.entity.Transaction;
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
        Transaction transactionEntity = modelMapper.map(transactionDto,Transaction.class);
        transactionRepo.save(transactionEntity);
    }
}
