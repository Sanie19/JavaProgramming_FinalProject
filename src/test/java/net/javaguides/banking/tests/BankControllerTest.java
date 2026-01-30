package net.javaguides.banking.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.banking.controller.BankController;
import net.javaguides.banking.dto.BankDto;
import net.javaguides.banking.exception.GlobalExceptionHandler;
import net.javaguides.banking.service.BankService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankController.class)
@Import(GlobalExceptionHandler.class)
class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BankService bankService;


    private BankDto bankDto(Long id) {
        return new BankDto(
                id,
                "Test Bank",
                "Main Branch",
                "Skopje"
        );
    }



    @Test
    void create_happyPath_returns200() throws Exception {
        when(bankService.create(any(BankDto.class)))
                .thenReturn(bankDto(1L));

        BankDto request = bankDto(null);

        mockMvc.perform(post("/api/banks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Bank"))
                .andExpect(jsonPath("$.branch").value("Main Branch"))
                .andExpect(jsonPath("$.address").value("Skopje"));
    }



    @Test
    void getById_happyPath_returns200() throws Exception {
        when(bankService.getById(1L))
                .thenReturn(bankDto(1L));

        mockMvc.perform(get("/api/banks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Bank"));
    }

    @Test
    void getById_notFound_returns404() throws Exception {
        when(bankService.getById(99L))
                .thenThrow(new RuntimeException("Bank not found"));

        mockMvc.perform(get("/api/banks/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Bank not found"));
    }



    @Test
    void getAll_happyPath_returns200_andList() throws Exception {
        when(bankService.getAll())
                .thenReturn(List.of(bankDto(1L), bankDto(2L)));

        mockMvc.perform(get("/api/banks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Test Bank"));
    }


    @Test
    void delete_happyPath_returns200() throws Exception {
        doNothing().when(bankService).delete(1L);

        mockMvc.perform(delete("/api/banks/1"))
                .andExpect(status().isOk());
    }

    @Test
    void delete_notFound_returns404() throws Exception {
        doThrow(new RuntimeException("Bank not found"))
                .when(bankService).delete(99L);

        mockMvc.perform(delete("/api/banks/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Bank not found"));
    }
}
