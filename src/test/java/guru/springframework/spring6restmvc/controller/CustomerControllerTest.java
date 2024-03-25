package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.service.CustomerService;
import guru.springframework.spring6restmvc.service.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    @Autowired
    ObjectMapper objectMapper;
    CustomerService customerServiceImpl;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CustomerDTO> customerArgumentCaptor;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }
//    CustomerService customerServiceImpl = new CustomerServiceImpl();

    @Test
    void patchById() throws Exception {
//        Customer customer = customerServiceImpl.listCustomers().get(0);

//        Map<String, Object> customerMap = new HashMap<>();
//        customerMap.put("name", "New Name");
        CustomerDTO customerDTO1 = guru.springframework.spring6restmvc.model.CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("New Name")
                .build();

        mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID, customerDTO1.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDTO1)))
                .andExpect(status().isNoContent());

        verify(customerService).patchById(uuidArgumentCaptor.capture(),
                customerArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(customerDTO1.getId());
        assertThat(customerArgumentCaptor.getValue().getCustomerName()).isEqualTo(customerDTO1.getCustomerName());
    }

    @Test
    void testPatchCustomer() {
        CustomerDTO customerDTO = guru.springframework.spring6restmvc.model.CustomerDTO.builder()
                .customerName("")
                .build();
    }

    @Test
    void testDeleteCustomer() throws Exception {
        CustomerDTO customerDTO = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, customerDTO.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(customerService).deleteById(uuidArgumentCaptor.capture());

        assertThat(customerDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }

    @Test
    void updateById() throws Exception {
        CustomerDTO customerDTO = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, customerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isNoContent());

        verify(customerService).updateById(any(UUID.class), any(CustomerDTO.class));
    }

    @Test
    void handlePost() throws Exception {
        CustomerDTO customerDTO = customerServiceImpl.listCustomers().get(0);
        customerDTO.setId(null);
        customerDTO.setVersion(null);

        given(customerService.saveCustomer(any(CustomerDTO.class)))
                .willReturn(customerServiceImpl.listCustomers().get(1));

        mockMvc.perform(post("/api/v1/customer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }

    @Test
    void listCustomers() throws Exception {
        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

        mockMvc.perform(get("/api/v1/customer").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO customerDTO = customerServiceImpl.listCustomers().get(0);

        given(customerService.getCustomerById(customerDTO.getId())).willReturn(Optional.of(customerDTO));

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, customerDTO.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(customerDTO.getId().toString())));
    }

    @Test
    void getCustomerByIdNotFound() throws Exception {
        given(customerService.getCustomerById(any(UUID.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}