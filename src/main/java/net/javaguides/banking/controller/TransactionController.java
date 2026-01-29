package net.javaguides.banking.controller;

import net.javaguides.banking.dto.TransactionDto;
import net.javaguides.banking.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionDto>> getByAccount(@PathVariable Long accountId){
        return ResponseEntity.ok(transactionService.getByAccountId(accountId));
    }
}
