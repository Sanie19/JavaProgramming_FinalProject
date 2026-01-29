package net.javaguides.banking.service.impl;

import net.javaguides.banking.dto.BankDto;
import net.javaguides.banking.entity.Bank;
import net.javaguides.banking.mapper.BankMapper;
import net.javaguides.banking.repository.BankRepository;
import net.javaguides.banking.service.BankService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    public BankServiceImpl(BankRepository bankRepository){
        this.bankRepository = bankRepository;
    }

    @Override
    public BankDto create(BankDto dto) {
        Bank b = BankMapper.mapToBank(dto);
        Bank saved = bankRepository.save(b);
        return BankMapper.mapToBankDto(saved);
    }

    @Override
    public BankDto getById(Long id) {
        Bank b = bankRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank not found"));
        return BankMapper.mapToBankDto(b);
    }

    @Override
    public List<BankDto> getAll() {
        return bankRepository.findAll().stream()
                .map(BankMapper::mapToBankDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if(!bankRepository.existsById(id)){
            throw new RuntimeException("Bank not found");
        }
        bankRepository.deleteById(id);
    }
}
