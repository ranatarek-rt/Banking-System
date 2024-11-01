package com.dragon.bankingSystem.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;


//use lombok annotations for getters and setters ,... etc creation
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="other_name")
    private String otherName;

    @Column(name = "email")
    private String email;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="alternative_phone_num")
    private String alternativePhoneNumber;

    @Column(name="address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name="gender")
    private Gender gender;

    @Column(name="account_number")
    private String accountNumber;

    @Column(name="account_balance")
    private BigDecimal accountBalance;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column(name="status")
    private String status;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="modified_at")
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

}
