package rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;
import rest.customer.CustomerController;
import rest.product.ProductController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@SuppressWarnings("unused")
public class RootApi extends ResourceSupport {

    private final String version;

    public RootApi(@JsonProperty("version") String version) {
        this.version = version;
        init();
    }

    private void init() {
        add(linkTo(methodOn(ProductController.class).getProductApi()).withRel("product"));
        add(linkTo(methodOn(CustomerController.class).getCustomerApi()).withRel("customer"));
        add(linkTo(methodOn(RootController.class).getApiDetails()).withSelfRel());
    }

    public String getVersion() {
        return version;
    }
}
