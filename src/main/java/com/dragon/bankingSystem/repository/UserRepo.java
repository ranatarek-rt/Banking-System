package com.dragon.bankingSystem.repository;

import com.dragon.bankingSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,String> {
    boolean existsByEmail(String email);
    Optional<User> findByAccountNumber(String accountNumber);
}
