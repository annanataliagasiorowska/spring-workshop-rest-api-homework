package pl.dgadecki.springworkshoprestapi.business.customer.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.dgadecki.springworkshoprestapi.business.customer.domain.service.CustomerService;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.Customer;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.api.*;
import pl.dgadecki.springworkshoprestapi.errorhandling.ErrorResponse;

@Tag(name = "Customer API", description = "All operations available for customers")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "The customers were successfully found", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FindAllCustomersResponse.class))
        }),
        @ApiResponse(responseCode = "400", description = "Bad request", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))
        }),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))
        })
})
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Find all customers", description = "Returns all customers in the system")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FindAllCustomersResponse findAllCustomers() {
        return FindAllCustomersResponse.fromCustomers(customerService.findAllCustomers());
    }

    @Operation(summary = "Create customer", description = "Given data from user creates new customer in the system")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateCustomerResponse createCustomer(
            @Parameter(description = "Data passed to create customer") @RequestBody CreateCustomerRequest createCustomerRequest
    ) {
        Customer createdCustomer = customerService.saveCustomer(createCustomerRequest.toCustomer());
        return CreateCustomerResponse.fromCustomer(createdCustomer);
    }

    @Operation(summary = "Update customer", description = "Updates customer found by id using data from request")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UpdateCustomerResponse updateCustomer(
            @Parameter(description = "Customer id") @PathVariable("id") Long customerId,
            @Parameter(description = "Data passed to update customer") @RequestBody UpdateCustomerRequest updateCustomerRequest
    ) {
        Customer updatedCustomer = customerService.updateCustomer(customerId, updateCustomerRequest.toCustomer());
        return UpdateCustomerResponse.fromCustomer(updatedCustomer);
    }

    @Operation(summary = "Delete customer", description = "Deletes customer by given id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteCustomer(
            @Parameter(description = "Customer id") @PathVariable("id") Long cusomerId
    ) {
        customerService.deleteCustomer(cusomerId);
    }

    @Operation(summary = "Find customer by id", description = "Given a valid id it returns proper customer, otherwise error")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FindCustomerResponse findCustomerById(
            @Parameter(description = "Customer id") @PathVariable("id") Long customerId
    ) {
        return FindCustomerResponse.fromCustomer(customerService.fetchCustomerById(customerId));
    }

    @Operation(summary = "Find customers by last name", description = "Get a list of customers by last name.")
    @ApiResponse(responseCode = "200", description = "Customers found by last name", content =
        @Content(schema = @Schema(implementation = FindAllCustomersResponse.class)))
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public FindAllCustomersResponse findCustomersByLastName(
            @Parameter(description = "Last name") @RequestParam("lastName") String lastName
    ) {
        return FindAllCustomersResponse.fromCustomers(customerService.fetchCustomerByLastName(lastName));
    }

    @Operation(summary = "Find a customer by email", description = "Get customer details by email.")
    @ApiResponse(responseCode = "200", description = "Customer details found by email", content =
        @Content(schema = @Schema(implementation = FindCustomerResponse.class)))
    @GetMapping(value = "/by-email", produces = MediaType.APPLICATION_JSON_VALUE)
    public FindCustomerResponse findCustomerByEmail(
            @Parameter(description = "Email") @RequestParam("email") String email
    ) {
        return FindCustomerResponse.fromCustomer(customerService.fetchCustomerByEmail(email));
    }

}
