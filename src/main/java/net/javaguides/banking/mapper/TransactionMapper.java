package net.javaguides.banking.mapper;

import net.javaguides.banking.dto.TransactionDto;
import net.javaguides.banking.entity.Transaction;

public class TransactionMapper {

    public static TransactionDto mapToTransactionDto(Transaction t){
        return new TransactionDto(
                t.getId(),
                t.getAmount(),
                t.getType(),
                t.getDate(),
                t.getAccount().getId()
        );
    }
}
