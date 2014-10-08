package rest.product;

import db.product.Product;
import db.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rest.AbstractController;
import rest.CollectionResource;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/api/product", produces = "application/hal+json")
public class ProductController extends AbstractController<Product, ProductResource> {

    private final ProductRepository repository;

    @Autowired
    public ProductController(ProductRepository repository, ProductResourceAssembler assembler) {
        super(repository, assembler);
        this.repository = repository;
    }

    @RequestMapping(method = GET)
    public HttpEntity<ProductApi> getProductApi() {
        ProductApi productApi = new ProductApi();
        return new ResponseEntity<>(productApi, HttpStatus.OK);
    }


    @RequestMapping(value = "/search", method = GET)
    @ResponseBody
    public HttpEntity<CollectionResource<ProductResource>> searchProducts(@RequestParam("terms") String terms) {
        List<Product> products = repository.searchByText(terms);

        CollectionResource<ProductResource> resource = new CollectionResource<>();
        resource.setItems(assembler.toResources(products));

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
