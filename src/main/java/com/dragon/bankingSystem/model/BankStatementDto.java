package com.dragon.bankingSystem.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BankStatementDto {
    private String accountNumber;
    private String startDate;
    private String endDate;

}
