package pl.dgadecki.springworkshoprestapi.business.customer.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.Customer;

@Schema(description = "Response representing an updated customer")
public record UpdateCustomerResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {
    public static UpdateCustomerResponse fromCustomer(Customer customer) {
        return new UpdateCustomerResponse(
                customer.id(),
                customer.firstName(),
                customer.lastName(),
                customer.email(),
                customer.phoneNumber()
        );
    }
}
