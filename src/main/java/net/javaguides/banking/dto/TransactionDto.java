package net.javaguides.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.javaguides.banking.entity.TransactionType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionDto {
    private Long id;
    private double amount;
    private TransactionType type;
    private LocalDateTime date;
    private Long accountId;
}
