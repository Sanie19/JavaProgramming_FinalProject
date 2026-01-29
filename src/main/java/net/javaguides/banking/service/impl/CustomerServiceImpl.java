package net.javaguides.banking.service.impl;

import net.javaguides.banking.dto.CustomerDto;
import net.javaguides.banking.entity.Customer;
import net.javaguides.banking.mapper.CustomerMapper;
import net.javaguides.banking.repository.CustomerRepository;
import net.javaguides.banking.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDto create(CustomerDto dto) {
        Customer c = CustomerMapper.mapToCustomer(dto);
        Customer saved = customerRepository.save(c);
        return CustomerMapper.mapToCustomerDto(saved);
    }

    @Override
    public CustomerDto getById(Long id) {
        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return CustomerMapper.mapToCustomerDto(c);
    }

    @Override
    public List<CustomerDto> getAll() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::mapToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if(!customerRepository.existsById(id)){
            throw new RuntimeException("Customer not found");
        }
        customerRepository.deleteById(id);
    }
}
