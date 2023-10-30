package pl.dgadecki.springworkshoprestapi.business.customer.dto.api;

import pl.dgadecki.springworkshoprestapi.business.customer.dto.Customer;

import java.util.List;
import java.util.stream.Collectors;

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
