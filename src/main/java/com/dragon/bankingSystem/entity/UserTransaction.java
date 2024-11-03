package com.dragon.bankingSystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="transactions")
public class UserTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="source_account_number")
    private String sourceAccountNumber;

    @Column(name="destination_account_number")
    private String destinationAccountNumber;

    @Column(name="amount")
    private BigDecimal amount;

    //debit, credit, transfer
    @Column(name="transaction_type")
    private String transactionType;

    //success, active, failed.. etc
    @Column(name="status")
    private String status;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

}
