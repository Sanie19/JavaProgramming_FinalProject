package net.javaguides.banking.controller;

import net.javaguides.banking.dto.BankDto;
import net.javaguides.banking.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banks")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService){
        this.bankService = bankService;
    }

    @PostMapping
    public ResponseEntity<BankDto> create(@RequestBody BankDto dto){
        return ResponseEntity.ok(bankService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(bankService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<BankDto>> getAll(){
        return ResponseEntity.ok(bankService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        bankService.delete(id);
        return ResponseEntity.ok("Bank deleted");
    }
}
