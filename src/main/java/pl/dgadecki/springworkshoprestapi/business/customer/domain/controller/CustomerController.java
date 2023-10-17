package pl.dgadecki.springworkshoprestapi.business.customer.domain.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dgadecki.springworkshoprestapi.business.customer.domain.service.CustomerService;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.Customer;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> findAllCustomers() {
        return new ResponseEntity<>(customerService.findAllCustomers(), HttpStatus.OK);
    }

    @PostMapping
    public void createCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);
    }

    @PutMapping("/{id}")
    public void updateCustomer(@PathVariable("id") Long customerId,
                               @RequestBody Customer customer) {
        customerService.updateCustomer(customerId, customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") Long cusomerId) {
        customerService.deleteCustomer(cusomerId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable("id") Long customerId) {
        return new ResponseEntity<>(customerService.fetchCustomerById(customerId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Customer>> findCustomersByLastName(
            @RequestParam("lastName") String lastName) {
        return new ResponseEntity<>(customerService.fetchCustomerByLastName(lastName), HttpStatus.OK);
    }

    @GetMapping("/by-email")
    public ResponseEntity<Customer> findCustomerByEmail(
            @RequestParam("email") String email) {
        return new ResponseEntity<>(customerService.fetchCustomerByEmail(email), HttpStatus.OK);
    }

}
