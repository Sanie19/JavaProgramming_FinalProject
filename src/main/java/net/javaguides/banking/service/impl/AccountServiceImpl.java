package net.javaguides.banking.service.impl;

import net.javaguides.banking.dto.AccountDto;
import net.javaguides.banking.entity.*;
import net.javaguides.banking.mapper.AccountMapper;
import net.javaguides.banking.repository.*;
import net.javaguides.banking.service.AccountService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final BankRepository bankRepository;
    private final TransactionRepository transactionRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                              CustomerRepository customerRepository,
                              BankRepository bankRepository,
                              TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.bankRepository = bankRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);

        if (accountDto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(accountDto.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            account.setCustomer(customer);
        }

        if (accountDto.getBankId() != null) {
            Bank bank = bankRepository.findById(accountDto.getBankId())
                    .orElseThrow(() -> new RuntimeException("Bank not found"));
            account.setBank(bank);
        }

        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        if (amount <= 0) throw new RuntimeException("Deposit amount must be > 0");

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);
        Account saved = accountRepository.save(account);

        Transaction t = new Transaction();
        t.setAmount(amount);
        t.setType(TransactionType.DEPOSIT);
        t.setDate(LocalDateTime.now());
        t.setAccount(saved);
        transactionRepository.save(t);

        return AccountMapper.mapToAccountDto(saved);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        if (amount <= 0) throw new RuntimeException("Withdraw amount must be > 0");

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient amount to withdraw");
        }

        account.setBalance(account.getBalance() - amount);
        Account saved = accountRepository.save(account);

        Transaction t = new Transaction();
        t.setAmount(amount);
        t.setType(TransactionType.WITHDRAW);
        t.setDate(LocalDateTime.now());
        t.setAccount(saved);
        transactionRepository.save(t);

        return AccountMapper.mapToAccountDto(saved);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(AccountMapper::mapToAccountDto)
                ///  e ben the stream to a list
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        if(!accountRepository.existsById(id)){
            throw new RuntimeException("Account not found");
        }
        accountRepository.deleteById(id);
    }
}
