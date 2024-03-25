package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customer";

    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";



    private final CustomerService customerService;

    @PatchMapping(value = CUSTOMER_PATH_ID)
    public ResponseEntity patchById(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customerDTO) {

        customerService.patchById(customerId, customerDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = CUSTOMER_PATH_ID)
    public ResponseEntity deteleById(@PathVariable("customerId") UUID customerId) {

        customerService.deleteById(customerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PutMapping(value = CUSTOMER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customerDTO) {

        customerService.updateById(customerId, customerDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PostMapping(value = CUSTOMER_PATH)
    public ResponseEntity handlePost(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO savedCustomerDTO = customerService.saveCustomer(customerDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_PATH + "/" + savedCustomerDTO.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = CUSTOMER_PATH)
    public List<CustomerDTO> listCustomers() {
        return this.customerService.listCustomers();
    }

    @GetMapping(value = CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable("customerId") UUID id) {

        log.debug("Get Customer by id - in controller");

        return this.customerService.getCustomerById(id).orElseThrow(NotFoundException::new);
    }
}
