package com.dragon.bankingSystem.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BankResponse {
    //to send back the status success, created, ..etc
    private String token;
    private String responseCode;
    private String responseMsg;
    private AccountInfo accountInfo;


}
