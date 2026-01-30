package net.javaguides.banking.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.banking.controller.CustomerController;
import net.javaguides.banking.dto.CustomerDto;
import net.javaguides.banking.exception.GlobalExceptionHandler;
import net.javaguides.banking.service.CustomerService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@Import(GlobalExceptionHandler.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    private CustomerDto customerDto(Long id) {
        return new CustomerDto(
                id,
                "Sanie",
                "sanie@gmail.com",
                "pass123",
                "+38970000000"
        );
    }



    @Test
    void create_happyPath_returns200() throws Exception {
        when(customerService.create(any(CustomerDto.class)))
                .thenReturn(customerDto(1L));

        CustomerDto request = customerDto(null);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sanie"))
                .andExpect(jsonPath("$.email").value("sanie@gmail.com"));
    }



    @Test
    void getById_happyPath_returns200() throws Exception {
        when(customerService.getById(1L)).thenReturn(customerDto(1L));

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Sanie"));
    }

    @Test
    void getById_notFound_returns404() throws Exception {
        when(customerService.getById(99L))
                .thenThrow(new RuntimeException("Customer not found"));

        mockMvc.perform(get("/api/customers/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Customer not found"));
    }



    @Test
    void getAll_happyPath_returns200_andList() throws Exception {
        when(customerService.getAll())
                .thenReturn(List.of(customerDto(1L), customerDto(2L)));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].email").value("sanie@gmail.com"));
    }

    @Test
    void getAll_empty_returns200_andEmptyList() throws Exception {
        when(customerService.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }



    @Test
    void delete_happyPath_returns200_andMessage() throws Exception {
        doNothing().when(customerService).delete(1L);

        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer deleted"));
    }

    @Test
    void delete_notFound_returns404() throws Exception {
        doThrow(new RuntimeException("Customer not found"))
                .when(customerService).delete(99L);

        mockMvc.perform(delete("/api/customers/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Customer not found"));
    }
}
