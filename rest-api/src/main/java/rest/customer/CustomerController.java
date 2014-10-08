package rest.customer;

import db.customer.Customer;
import db.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rest.AbstractController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/customer")
public class CustomerController extends AbstractController<Customer, CustomerResource> {

    @Autowired
    public CustomerController(CustomerRepository repository, CustomerResourceAssembler assembler) {
        super(repository, assembler);
    }

    @RequestMapping(method = GET)
    public HttpEntity<CustomerApi> getCustomerApi()  {
        CustomerApi customerApi = new CustomerApi();
        return new ResponseEntity<>(customerApi, HttpStatus.OK);
    }
}
