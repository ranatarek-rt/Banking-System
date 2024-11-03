package com.dragon.bankingSystem.repository;

import com.dragon.bankingSystem.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<UserTransaction,String> {
    List<UserTransaction> findBySourceAccountNumber(String sourceAccountNumber);
}
