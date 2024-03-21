package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    Customer saveCustomer(Customer customer);

    Customer getCustomerById(UUID id);
    List<Customer> listCustomers();
}
