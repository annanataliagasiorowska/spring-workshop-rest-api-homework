package pl.dgadecki.springworkshoprestapi.business.customer.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.Customer;

@Schema(description = "Response representing customer found by given criteria")
public record FindCustomerResponse(
        Long id,
       String firstName,
       String lastName,
       String email,
       String phoneNumber
) {
    public static FindCustomerResponse fromCustomer(Customer customer) {
        return new FindCustomerResponse(
                customer.id(),
                customer.firstName(),
                customer.lastName(),
                customer.email(),
                customer.phoneNumber()
        );
    }
}
