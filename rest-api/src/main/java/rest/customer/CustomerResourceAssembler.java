package rest.customer;

import db.customer.Customer;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static rest.customer.CustomerResource.CustomerAddressResource;

@Component
public class CustomerResourceAssembler extends ResourceAssemblerSupport<Customer, CustomerResource> {

    public CustomerResourceAssembler() {
        super(CustomerController.class, CustomerResource.class);
    }

    @Override
    public CustomerResource toResource(Customer customer) {
        CustomerResource resource = createResourceWithId(customer.id(), customer);

        resource.setFirstName(customer.firstName());
        resource.setLastName(customer.lastName());

        CustomerAddressResource addressResource = new CustomerAddressResource();
        addressResource.setNumber(customer.invoiceAddress().number());
        addressResource.setStreet(customer.invoiceAddress().street());
        addressResource.setCity(customer.invoiceAddress().city());
        addressResource.setPostcode(customer.invoiceAddress().postcode());

        resource.setInvoiceAddress(addressResource);

        return resource;
    }
}
