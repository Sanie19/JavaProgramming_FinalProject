package net.javaguides.banking.tests;

import net.javaguides.banking.controller.TransactionController;
import net.javaguides.banking.dto.TransactionDto;
import net.javaguides.banking.entity.TransactionType;
import net.javaguides.banking.exception.GlobalExceptionHandler;
import net.javaguides.banking.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@Import(GlobalExceptionHandler.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private TransactionDto transactionDto(Long id, Long accountId) {
        return new TransactionDto(
                id,
                100.0,
                TransactionType.DEPOSIT,
                LocalDateTime.of(2026, 1, 1, 12, 0),
                accountId
        );
    }



    @Test
    void getByAccount_happyPath_returns200_andList() throws Exception {
        when(transactionService.getByAccountId(1L))
                .thenReturn(List.of(
                        transactionDto(1L, 1L),
                        transactionDto(2L, 1L)
                ));

        mockMvc.perform(get("/api/transactions/account/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].type").value("DEPOSIT"))
                .andExpect(jsonPath("$[0].accountId").value(1));
    }

    @Test
    void getByAccount_empty_returns200_andEmptyList() throws Exception {
        when(transactionService.getByAccountId(99L)).thenReturn(List.of());

        mockMvc.perform(get("/api/transactions/account/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
