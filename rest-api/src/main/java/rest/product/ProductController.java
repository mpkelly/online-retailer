package rest.product;

import db.product.Product;
import db.product.ProductRepository;
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
@RequestMapping(value = "/api/product", produces = "application/hal+json")
public class ProductController {

    static final String DEFAULT_PAGE_SIZE = "50";

    private final ProductResourceAssembler assembler;
    private final ProductRepository repository;

    @Autowired
    public ProductController(ProductResourceAssembler assembler, ProductRepository repository) {
        this.assembler = assembler;
        this.repository = repository;
    }

    @RequestMapping(method = GET)
    public HttpEntity<ProductApi> getProductApi() {
        ProductApi productApi = new ProductApi();
        return new ResponseEntity<>(productApi, HttpStatus.OK);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Void> createProduct(@RequestBody String json)  {
        Product inserted = repository.insert(jsonToMap(json));

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(getClass()).findProductById(inserted.id())).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = GET)
    @ResponseBody
    public HttpEntity<ProductResource> findProductById(@PathVariable("id") String id) {
        Product product = repository.findById(id);

        ProductResource productResource = assembler.toResource(product);

        return new ResponseEntity<>(productResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = GET)
    @ResponseBody
    public HttpEntity<ProductsResource> searchProducts(@RequestParam("terms") String terms) {
        List<Product> products = repository.searchByText(terms);

        ProductsResource resource = new ProductsResource();
        resource.setProducts(assembler.toResources(products));

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/browse", method = GET)
    @ResponseBody
    public HttpEntity<ProductsResource> getPage(@RequestParam(value = "pageSize", required = false, defaultValue = "1") Integer pageSize,
                                            @RequestParam(value = "pageNumber", required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageNumber) {

        List<Product> products = repository.browse(pageSize, pageNumber);

        long count = repository.count();
        int lastPage = (int) Math.ceil((double)count / pageSize);

        ProductsResource resource = new ProductsResource();
        resource.setProducts(assembler.toResources(products));

        resource.add(linkTo(methodOn(getClass()).getPage(pageSize, pageNumber)).withSelfRel());
        resource.add(linkTo(methodOn(getClass()).getPage(pageSize, 1)).withRel("firstPage"));
        resource.add(linkTo(methodOn(getClass()).getPage(pageSize, lastPage)).withRel("lastPage"));

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
