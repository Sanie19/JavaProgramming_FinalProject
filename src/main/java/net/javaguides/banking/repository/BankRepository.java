package net.javaguides.banking.repository;

import net.javaguides.banking.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {}
