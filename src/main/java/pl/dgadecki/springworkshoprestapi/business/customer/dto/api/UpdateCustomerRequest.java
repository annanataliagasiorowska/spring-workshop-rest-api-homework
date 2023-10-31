package pl.dgadecki.springworkshoprestapi.business.customer.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.Customer;

@Schema(description = "Request representing customer to update")
public record UpdateCustomerRequest(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {
    public Customer toCustomer() { return new Customer(id, firstName, lastName, email, phoneNumber); }
}
