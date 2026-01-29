package net.javaguides.banking.service;

import net.javaguides.banking.dto.BankDto;

import java.util.List;

public interface BankService {
    BankDto create(BankDto dto);
    BankDto getById(Long id);
    List<BankDto> getAll();
    void delete(Long id);
}
