package net.javaguides.banking.mapper;

import net.javaguides.banking.dto.CustomerDto;
import net.javaguides.banking.entity.Customer;

public class CustomerMapper {

    public static Customer mapToCustomer(CustomerDto dto){
        Customer c = new Customer();
        c.setId(dto.getId());
        c.setName(dto.getName());
        c.setEmail(dto.getEmail());
        c.setPassword(dto.getPassword());
        c.setPhone(dto.getPhone());
        return c;
    }

    public static CustomerDto mapToCustomerDto(Customer c){
        return new CustomerDto(
                c.getId(),
                c.getName(),
                c.getEmail(),
                c.getPassword(),
                c.getPhone()
        );
    }
}
