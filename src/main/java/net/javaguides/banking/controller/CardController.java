package net.javaguides.banking.controller;

import net.javaguides.banking.dto.CardDto;
import net.javaguides.banking.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService){
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<CardDto> create(@RequestBody CardDto dto){
        return ResponseEntity.ok(cardService.create(dto));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<List<CardDto>> getByAccount(@PathVariable Long accountId){
        return ResponseEntity.ok(cardService.getByAccountId(accountId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        cardService.delete(id);
        return ResponseEntity.ok("Card deleted");
    }
}
