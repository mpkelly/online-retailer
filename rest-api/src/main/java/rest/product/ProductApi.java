package rest.product;

import org.springframework.hateoas.ResourceSupport;

import static java.lang.Integer.parseInt;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static rest.product.ProductController.DEFAULT_PAGE_SIZE;

public class ProductApi extends ResourceSupport {

    public ProductApi() {
        init();
    }

    private void init() {
        Class<ProductController> controller = ProductController.class; 
        add(linkTo(methodOn(controller).getProductApi()).withSelfRel());
        add(linkTo(methodOn(controller).findProductById(":id")).withRel("find"));
        add(linkTo(methodOn(controller).createProduct(":json")).withRel("create"));
        add(linkTo(methodOn(controller).searchProducts("terms")).withRel("search"));
        add(linkTo(methodOn(controller).getPage(parseInt(DEFAULT_PAGE_SIZE), 1)).withRel("browse"));
    }
}
