package com.dragon.bankingSystem.model;

import com.dragon.bankingSystem.entity.Gender;
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
    private String phoneNumber;
    private String alternativePhoneNumber;
    private String address;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private BigDecimal accountBalance;
    private String placeOfBirth;
    private String status;

}
