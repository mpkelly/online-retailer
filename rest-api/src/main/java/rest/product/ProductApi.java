package rest.product;

import org.springframework.hateoas.ResourceSupport;

import static java.lang.Integer.parseInt;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static rest.AbstractController.DEFAULT_PAGE_SIZE;

public class ProductApi extends ResourceSupport {

    public ProductApi() {
        init();
    }

    private void init() {
        Class<ProductController> controller = ProductController.class; 
        add(linkTo(methodOn(controller).getProductApi()).withSelfRel());
        add(linkTo(methodOn(controller).findById(":id")).withRel("find"));
        add(linkTo(methodOn(controller).create(":json")).withRel("create"));
        add(linkTo(methodOn(controller).searchProducts("terms")).withRel("search"));
        add(linkTo(methodOn(controller).browse(parseInt(DEFAULT_PAGE_SIZE), 1)).withRel("browse"));
    }
}
