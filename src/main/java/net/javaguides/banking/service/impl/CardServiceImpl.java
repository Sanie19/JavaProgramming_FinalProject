package net.javaguides.banking.service.impl;

import net.javaguides.banking.dto.CardDto;
import net.javaguides.banking.entity.Account;
import net.javaguides.banking.entity.Card;
import net.javaguides.banking.mapper.CardMapper;
import net.javaguides.banking.repository.AccountRepository;
import net.javaguides.banking.repository.CardRepository;
import net.javaguides.banking.service.CardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    public CardServiceImpl(CardRepository cardRepository, AccountRepository accountRepository){
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public CardDto create(CardDto dto) {
        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Card c = new Card();
        c.setCardNumber(dto.getCardNumber());
        c.setCardHolderName(dto.getCardHolderName());
        c.setExpiryDate(dto.getExpiryDate());
        c.setAccount(account);

        Card saved = cardRepository.save(c);
        return CardMapper.mapToCardDto(saved);
    }

    @Override
    public List<CardDto> getByAccountId(Long accountId) {
        return cardRepository.findByAccountId(accountId).stream()
                .map(CardMapper::mapToCardDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if(!cardRepository.existsById(id)){
            throw new RuntimeException("Card not found");
        }
        cardRepository.deleteById(id);
    }
}
