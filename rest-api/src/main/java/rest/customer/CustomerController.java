package rest.customer;

import db.customer.Customer;
import db.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static rest.util.Json.jsonToMap;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    static final String DEFAULT_PAGE_SIZE = "50";

    private final CustomerRepository repository;
    private final CustomerResourceAssembler assembler;

    @Autowired
    public CustomerController(CustomerRepository repository, CustomerResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @RequestMapping(method = GET)
    public HttpEntity<CustomerApi> getCustomerApi()  {
        CustomerApi customerApi = new CustomerApi();
        return new ResponseEntity<>(customerApi, HttpStatus.OK);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Void> createCustomer(@RequestBody String json) {
        Customer customer = repository.insert(jsonToMap(json));

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(getClass()).findCustomerById(customer.id())).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = GET)
    public HttpEntity<CustomerResource> findCustomerById(@PathVariable("id") String id) {
        Customer customer = repository.findById(id);
        CustomerResource customerResource = assembler.toResource(customer);

        return new ResponseEntity<>(customerResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/browse", method = GET)
    public HttpEntity<CustomersResource> getPage(@RequestParam(value = "pageSize", required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
                                                           @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber) {

        List<Customer> customers = repository.browse(pageSize, pageNumber);

        CustomersResource customersResource = new CustomersResource();
        customersResource.setCustomers(assembler.toResources(customers));

        long count = repository.count();
        int lastPage = (int) Math.ceil((double)count / pageSize);

        customersResource.add(linkTo(methodOn(getClass()).getPage(pageSize, pageNumber)).withSelfRel());
        customersResource.add(linkTo(methodOn(getClass()).getPage(pageSize, 1)).withRel("firstPage"));
        customersResource.add(linkTo(methodOn(getClass()).getPage(pageSize, lastPage)).withRel("lastPage"));

        return new ResponseEntity<>(customersResource, HttpStatus.OK);
    }
}
