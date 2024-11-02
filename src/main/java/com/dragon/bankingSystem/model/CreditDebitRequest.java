package com.dragon.bankingSystem.model;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreditDebitRequest {
    private String accountNumber;
    //for the amount one user want to credit or debit
    private BigDecimal amount;

}
