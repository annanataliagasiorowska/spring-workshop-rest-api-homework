package pl.dgadecki.springworkshoprestapi.business.customer.dto.api;

import pl.dgadecki.springworkshoprestapi.business.customer.dto.Customer;

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
