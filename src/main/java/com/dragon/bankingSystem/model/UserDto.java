package com.dragon.bankingSystem.model;

import com.dragon.bankingSystem.entity.Gender;
import com.dragon.bankingSystem.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

    private String firstName;
    private String lastName;
    private String otherName;
    private String email;
    private String pass;
    private String phoneNumber;
    private String alternativePhoneNumber;
    private String address;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Role role;
    private BigDecimal accountBalance;
    private String placeOfBirth;
    private String status;

}
