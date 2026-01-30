package net.javaguides.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data    //auto getters/setters
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String accountHolderName;
    private double balance;

    // ketu links account to customer
    private Long customerId;
    private Long bankId;
    // one bank , many accounts
}
// server-client