package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService{

    private Map<UUID, Customer> customerMap;

    @Override
    public Customer saveCustomer(Customer customer) {
        Customer saveCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .customerName(customer.getCustomerName())
                .version(1)
                .createDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        customerMap.put(saveCustomer.getId(), saveCustomer);
        return saveCustomer;
    }
    public CustomerServiceImpl () {
        this.customerMap = new HashMap<>();

        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("John")
                .version(1)
                .createDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("James")
                .version(1)
                .createDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Smith")
                .version(1)
                .createDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        this.customerMap.put(customer1.getId(), customer1);
        this.customerMap.put(customer2.getId(), customer2);
        this.customerMap.put(customer3.getId(), customer3);
    }
    @Override
    public Customer getCustomerById(UUID id) {
        return this.customerMap.get(id);
    }

    @Override
    public List<Customer> listCustomers() {
        return new ArrayList<>(this.customerMap.values());
    }
}
