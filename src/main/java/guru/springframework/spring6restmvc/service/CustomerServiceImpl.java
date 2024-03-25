package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService{

    private Map<UUID, CustomerDTO> customerMap;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        CustomerDTO saveCustomerDTO = guru.springframework.spring6restmvc.model.CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName(customerDTO.getCustomerName())
                .version(1)
                .createDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        customerMap.put(saveCustomerDTO.getId(), saveCustomerDTO);
        return saveCustomerDTO;
    }
    public CustomerServiceImpl () {
        this.customerMap = new HashMap<>();

        CustomerDTO customerDTO1 = guru.springframework.spring6restmvc.model.CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("John")
                .version(1)
                .createDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        CustomerDTO customerDTO2 = guru.springframework.spring6restmvc.model.CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("James")
                .version(1)
                .createDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        CustomerDTO customerDTO3 = guru.springframework.spring6restmvc.model.CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Smith")
                .version(1)
                .createDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        this.customerMap.put(customerDTO1.getId(), customerDTO1);
        this.customerMap.put(customerDTO2.getId(), customerDTO2);
        this.customerMap.put(customerDTO3.getId(), customerDTO3);
    }
    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.of(this.customerMap.get(id));
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<>(this.customerMap.values());
    }

    @Override
    public void updateById(UUID customerId, CustomerDTO customerDTO) {
        CustomerDTO existing = customerMap.get(customerId);
        existing.setCustomerName(customerDTO.getCustomerName());

        customerMap.put(existing.getId(), existing);
    }

    @Override
    public void deleteById(UUID customerId) {
        customerMap.remove(customerId);
    }

    @Override
    public void patchById(UUID customerId, CustomerDTO customerDTO) {
        CustomerDTO existing = customerMap.get(customerId);

        if(StringUtils.hasText(existing.getCustomerName())) {
            existing.setCustomerName(customerDTO.getCustomerName());
        }


    }
}
