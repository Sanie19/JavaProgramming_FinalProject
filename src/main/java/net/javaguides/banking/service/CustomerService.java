package net.javaguides.banking.service;

import net.javaguides.banking.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    CustomerDto create(CustomerDto dto);
    CustomerDto getById(Long id);
    List<CustomerDto> getAll();
    void delete(Long id);
}
