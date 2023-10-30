package pl.dgadecki.springworkshoprestapi.business.customer.domain.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.dgadecki.springworkshoprestapi.business.customer.domain.service.CustomerService;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.Customer;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.api.*;

@ApiResponses({
        @ApiResponse(responseCode = "200", description = "The customers were successfully found", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FindAllCustomersResponse.class))
        })

})
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public FindAllCustomersResponse findAllCustomers() {
        return FindAllCustomersResponse.fromCustomers(customerService.findAllCustomers());
    }

    @PostMapping
    public CreateCustomerResponse createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        Customer createdCustomer = customerService.saveCustomer(createCustomerRequest.toCustomer());
        return CreateCustomerResponse.fromCustomer(createdCustomer);
    }

    @PutMapping("/{id}")
    public UpdateCustomerResponse updateCustomer(@PathVariable("id") Long customerId,
                                                 @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        Customer updatedCustomer = customerService.updateCustomer(customerId, updateCustomerRequest.toCustomer());
        return UpdateCustomerResponse.fromCustomer(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") Long cusomerId) {
        customerService.deleteCustomer(cusomerId);
    }

    @GetMapping("/{id}")
    public FindCustomerResponse findCustomerById(@PathVariable("id") Long customerId) {
        return FindCustomerResponse.fromCustomer(customerService.fetchCustomerById(customerId));
    }

    @GetMapping("/search")
    public FindAllCustomersResponse findCustomersByLastName(
            @RequestParam("lastName") String lastName) {
        return FindAllCustomersResponse.fromCustomers(customerService.fetchCustomerByLastName(lastName));
    }

    @GetMapping("/by-email")
    public FindCustomerResponse findCustomerByEmail(
            @RequestParam("email") String email) {
        return FindCustomerResponse.fromCustomer(customerService.fetchCustomerByEmail(email));
    }

}
