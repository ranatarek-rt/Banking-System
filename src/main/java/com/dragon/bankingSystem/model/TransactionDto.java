package com.dragon.bankingSystem.model;

import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionDto {

    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private BigDecimal amount;
    //debit, credit, transfer
    private String transactionType;
    //success, active, failed.. etc
    private String status;

}
