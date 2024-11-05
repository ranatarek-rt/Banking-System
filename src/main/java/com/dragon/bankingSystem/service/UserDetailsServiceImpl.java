package com.dragon.bankingSystem.service;

import com.dragon.bankingSystem.entity.User;
import com.dragon.bankingSystem.model.UserDto;
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
        Optional<User> optUser = userRepo.findByEmail(username);
        User existingUser = null;
        if(optUser.isPresent()){
            existingUser = optUser.get();
            return
                    new User(existingUser.getId(),existingUser.getFirstName(),
                            existingUser.getLastName(),existingUser.getOtherName(),
                            existingUser.getEmail());
        }else{
            System.out.println("user not found");
            throw new UsernameNotFoundException("user Name not found");
        }

    }
}
