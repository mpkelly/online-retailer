package rest.product;

import db.product.Product;
import db.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static rest.util.Json.jsonToMap;

@RestController
public class ProductController {

    private final ProductRepository repository;

    @Autowired
    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/api/product", method = GET)
    public HttpEntity<ProductApi> getProductApi() throws IOException {
        ProductApi productApi = new ProductApi();

        productApi.add(linkTo(methodOn(this.getClass()).findProductById(":id")).withRel("find-by-id"));
        productApi.add(linkTo(methodOn(this.getClass()).createProduct(":json")).withRel("create-product"));

        return new ResponseEntity<>(productApi, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/product", method = POST)
    public ResponseEntity<Void> createProduct(@RequestBody String json) throws IOException {
        Product inserted = repository.insert(jsonToMap(json));

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(getClass()).findProductById(inserted.id())).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping("/api/product/id/{id}")
    @ResponseBody
    public HttpEntity<JsonProduct> findProductById(@PathVariable("id") String id) {
        Product product = repository.findById(id);
        return new ResponseEntity<>(new JsonProduct(product), HttpStatus.OK);
    }
}
