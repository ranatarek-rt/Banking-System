package com.dragon.bankingSystem.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountInfo {
    private String accountName;
    private String accountNumber;
    private BigDecimal accountBalance;
}
