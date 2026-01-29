package net.javaguides.banking.service.impl;

import net.javaguides.banking.dto.TransactionDto;
import net.javaguides.banking.mapper.TransactionMapper;
import net.javaguides.banking.repository.TransactionRepository;
import net.javaguides.banking.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<TransactionDto> getByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId).stream()
                .map(TransactionMapper::mapToTransactionDto)
                .collect(Collectors.toList());
    }
}
