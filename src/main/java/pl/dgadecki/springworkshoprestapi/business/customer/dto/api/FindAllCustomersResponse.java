package pl.dgadecki.springworkshoprestapi.business.customer.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import pl.dgadecki.springworkshoprestapi.business.customer.dto.Customer;

import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "Response representing all customers or customers found by given criteria")
public record FindAllCustomersResponse(
        List<FindCustomerResponse> findCustomerResponseList
) {
    public static FindAllCustomersResponse fromCustomers(List<Customer> customers) {
        List<FindCustomerResponse> findCustomerResponses = customers.stream()
                .map(FindCustomerResponse::fromCustomer)
                .collect(Collectors.toList());
        return new FindAllCustomersResponse(findCustomerResponses);
    }
}
