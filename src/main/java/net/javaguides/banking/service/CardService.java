package net.javaguides.banking.service;

import net.javaguides.banking.dto.CardDto;

import java.util.List;

public interface CardService {
    CardDto create(CardDto dto);
    List<CardDto> getByAccountId(Long accountId);
    void delete(Long id);
}
