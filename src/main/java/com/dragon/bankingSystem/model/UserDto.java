package com.dragon.bankingSystem.model;

import com.dragon.bankingSystem.entity.Gender;
import com.dragon.bankingSystem.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto  {

    private String firstName;
    private String lastName;
    private String otherName;
    private String email;
    private String password;
    private String phoneNumber;
    private String alternativePhoneNumber;
    private String address;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private BigDecimal accountBalance;
    private String placeOfBirth;

}
