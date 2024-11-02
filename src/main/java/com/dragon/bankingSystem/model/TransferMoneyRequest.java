package com.dragon.bankingSystem.model;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferMoneyRequest {

    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private BigDecimal transferAmount;
}
