package com.dragon.bankingSystem.repository;

import com.dragon.bankingSystem.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction,String> {
}
