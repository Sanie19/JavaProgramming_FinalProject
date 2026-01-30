package net.javaguides.banking.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.banking.controller.CardController;
import net.javaguides.banking.dto.CardDto;
import net.javaguides.banking.exception.GlobalExceptionHandler;
import net.javaguides.banking.service.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardController.class)
@Import(GlobalExceptionHandler.class)
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CardService cardService;

    private CardDto cardDto(Long id, Long accountId) {
        return new CardDto(
                id,
                "4539123412345678",
                "Sanie",
                LocalDate.of(2028, 6, 30),
                accountId
        );
    }



    @Test
    void create_happyPath_returns200() throws Exception {
        when(cardService.create(any(CardDto.class))).thenReturn(cardDto(1L, 10L));

        CardDto request = cardDto(null, 10L);

        mockMvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber").value("4539123412345678"))
                .andExpect(jsonPath("$.cardHolderName").value("Sanie"))
                .andExpect(jsonPath("$.accountId").value(10));
    }



    @Test
    void getByAccount_happyPath_returns200_andList() throws Exception {
        when(cardService.getByAccountId(10L))
                .thenReturn(List.of(cardDto(1L, 10L), cardDto(2L, 10L)));

        mockMvc.perform(get("/api/cards/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].accountId").value(10));
    }

    @Test
    void getByAccount_empty_returns200_andEmptyList() throws Exception {
        when(cardService.getByAccountId(10L)).thenReturn(List.of());

        mockMvc.perform(get("/api/cards/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }



    @Test
    void delete_happyPath_returns200_andMessage() throws Exception {
        doNothing().when(cardService).delete(1L);

        mockMvc.perform(delete("/api/cards/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Card deleted"));
    }

    @Test
    void delete_notFound_returns404() throws Exception {
        doThrow(new RuntimeException("Card not found")).when(cardService).delete(999L);

        mockMvc.perform(delete("/api/cards/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Card not found"));
    }
}
