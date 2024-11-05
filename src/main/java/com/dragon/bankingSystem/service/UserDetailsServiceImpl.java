package com.dragon.bankingSystem.service;

import com.dragon.bankingSystem.entity.User;
import com.dragon.bankingSystem.model.UserPrinciples;
import com.dragon.bankingSystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepo userRepo;

    @Autowired
    UserDetailsServiceImpl(UserRepo userRepo){
        this.userRepo = userRepo;

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //we can change the find with first name to find user by email
        Optional<User> optUser = userRepo.findByFirstName(username);
        User existingUser = null;
        if(optUser.isPresent()){
            existingUser = optUser.get();
            return new UserPrinciples(existingUser);
        }else{
            System.out.println("user not found");
            throw new UsernameNotFoundException("user Name not found");
        }

    }
}
