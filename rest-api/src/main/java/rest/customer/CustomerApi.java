package rest.customer;

import org.springframework.hateoas.ResourceSupport;

import static java.lang.Integer.parseInt;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static rest.customer.CustomerController.DEFAULT_PAGE_SIZE;

public class CustomerApi extends ResourceSupport {

    public CustomerApi() {
        init();
    }

    private void init() {
        Class<CustomerController> controller = CustomerController.class;

        add(linkTo(methodOn(controller).getCustomerApi()).withSelfRel());
        add(linkTo(methodOn(controller).createCustomer(":customer")).withRel("create"));
        add(linkTo(methodOn(controller).getPage(parseInt(DEFAULT_PAGE_SIZE), 1)).withRel("browse"));
        add(linkTo(methodOn(controller).findCustomerById(":id")).withRel("find"));
    }
}
