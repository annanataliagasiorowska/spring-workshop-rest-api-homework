package pl.dgadecki.springworkshoprestapi.business.customer.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.Customer;

@Schema(description = "Response representing a customer")
public record CreateCustomerResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {
    public static CreateCustomerResponse fromCustomer(Customer customer) {
        return new CreateCustomerResponse(
                customer.id(),
                customer.firstName(),
                customer.lastName(),
                customer.email(),
                customer.phoneNumber()
        );
    }
}
