package net.javaguides.banking.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.banking.controller.AccountController;
import net.javaguides.banking.dto.AccountDto;
import net.javaguides.banking.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private AccountService accountService;

    private AccountDto dto(Long id, double balance) {
        return new AccountDto(id, "Sanie", balance, 1L, 1L);
    }

    @Test
    void addAccount_happyPath_returns201() throws Exception {
        when(accountService.createAccount(any(AccountDto.class))).thenReturn(dto(1L, 0.0));

        AccountDto req = dto(null, 0.0);

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAccountById_happyPath_returns200() throws Exception {
        when(accountService.getAccountById(1L)).thenReturn(dto(1L, 100.0));

        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAccountById_notFound_returns404() throws Exception {
        when(accountService.getAccountById(99L))
                .thenThrow(new RuntimeException("Account not found"));

        mockMvc.perform(get("/api/accounts/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Account not found"));
    }

    @Test
    void deposit_happyPath_returns200() throws Exception {
        when(accountService.deposit(eq(1L), eq(50.0))).thenReturn(dto(1L, 150.0));

        mockMvc.perform(put("/api/accounts/1/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("amount", 50.0))))
                .andExpect(status().isOk());
    }

    @Test
    void deposit_negative_returns400() throws Exception {
        when(accountService.deposit(eq(1L), eq(-10.0)))
                .thenThrow(new RuntimeException("Deposit amount must be > 0"));

        mockMvc.perform(put("/api/accounts/1/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("amount", -10.0))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void withdraw_happyPath_returns200() throws Exception {
        when(accountService.withdraw(eq(1L), eq(20.0))).thenReturn(dto(1L, 80.0));

        mockMvc.perform(put("/api/accounts/1/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("amount", 20.0))))
                .andExpect(status().isOk());
    }

    @Test
    void withdraw_insufficient_returns400() throws Exception {
        when(accountService.withdraw(eq(1L), eq(9999.0)))
                .thenThrow(new RuntimeException("Insufficient amount to withdraw"));

        mockMvc.perform(put("/api/accounts/1/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("amount", 9999.0))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllAccounts_returns200_andList() throws Exception {
        when(accountService.getAllAccounts()).thenReturn(List.of(dto(1L, 10.0), dto(2L, 20.0)));

        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void deleteAccount_happyPath_returns200() throws Exception {
        doNothing().when(accountService).deleteAccount(1L);

        mockMvc.perform(delete("/api/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Account deleted"));
    }
}
