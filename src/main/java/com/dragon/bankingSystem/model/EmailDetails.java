package com.dragon.bankingSystem.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailDetails {

    private String recipient;
    private String messageBody;
    private String subject;
    private String attachment;
}
