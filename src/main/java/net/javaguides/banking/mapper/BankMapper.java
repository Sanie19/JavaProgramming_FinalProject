package net.javaguides.banking.mapper;

import net.javaguides.banking.dto.BankDto;
import net.javaguides.banking.entity.Bank;

public class BankMapper {

    public static Bank mapToBank(BankDto dto){
        Bank b = new Bank();
        b.setId(dto.getId());
        b.setName(dto.getName());
        b.setBranch(dto.getBranch());
        b.setAddress(dto.getAddress());
        return b;
    }

    public static BankDto mapToBankDto(Bank b){
        return new BankDto(
                b.getId(),
                b.getName(),
                b.getBranch(),
                b.getAddress()
        );
    }
}
