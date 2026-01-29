package net.javaguides.banking.service;

import net.javaguides.banking.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> getByAccountId(Long accountId);
}
