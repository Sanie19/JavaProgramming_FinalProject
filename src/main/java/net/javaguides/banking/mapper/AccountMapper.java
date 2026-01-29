package net.javaguides.banking.mapper;

import net.javaguides.banking.dto.AccountDto;
import net.javaguides.banking.entity.Account;

public class AccountMapper {

    public static Account mapToAccount(AccountDto dto) {
        Account a = new Account();
        a.setId(dto.getId());
        a.setAccountHolderName(dto.getAccountHolderName());
        a.setBalance(dto.getBalance());

        return a;
    }

    public static AccountDto mapToAccountDto(Account a) {
        Long customerId = (a.getCustomer() != null) ? a.getCustomer().getId() : null;
        Long bankId = (a.getBank() != null) ? a.getBank().getId() : null;

        return new AccountDto(
                a.getId(),
                a.getAccountHolderName(),
                a.getBalance(),
                customerId,
                bankId
        );
    }
}
