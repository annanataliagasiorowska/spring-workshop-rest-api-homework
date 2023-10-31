package pl.dgadecki.springworkshoprestapi.business.customer.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.Customer;

@Schema(description = "Request to create a new customer")
public record CreateCustomerRequest(
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {
    public Customer toCustomer() {
        return new Customer(null, firstName, lastName, email, phoneNumber);
    }
}
