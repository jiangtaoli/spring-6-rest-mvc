package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    Optional<CustomerDTO> getCustomerById(UUID id);
    List<CustomerDTO> listCustomers();

    void updateById(UUID customerId, CustomerDTO customerDTO);

    void deleteById(UUID customerId);

    void patchById(UUID customerId, CustomerDTO customerDTO);
}
