package com.dragon.bankingSystem.model;

import com.dragon.bankingSystem.entity.UserTransaction;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BankTransactionResponse {

    //to send back the status success, created, ..etc
    private String responseCode;
    private String responseMsg;
    private List<UserTransaction> transactionList;
}
