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
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static rest.product.JsonProducts.jsonProducts;
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

        productApi.add(linkTo(methodOn(this.getClass()).getProductApi()).withSelfRel());
        productApi.add(linkTo(methodOn(this.getClass()).findProductById(":id")).withRel("find-by-id"));
        productApi.add(linkTo(methodOn(this.getClass()).createProduct(":json")).withRel("create-product"));
        productApi.add(linkTo(methodOn(this.getClass()).searchProducts(":terms")).withRel("search-products"));
        productApi.add(linkTo(methodOn(this.getClass()).allProducts()).withRel("all-products"));

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

        JsonProduct jsonProduct = new JsonProduct(product);
        jsonProduct.add(linkTo(methodOn(getClass()).findProductById(id)).withSelfRel());

        return new ResponseEntity<>(jsonProduct, HttpStatus.OK);
    }

    @RequestMapping("/api/product/search/{terms}")
    @ResponseBody
    public HttpEntity<JsonProducts> searchProducts(@PathVariable("terms") String terms) {
        List<Product> products = repository.searchByText(terms);

        JsonProducts jsonProducts = jsonProducts(products);
        jsonProducts.add(linkTo(methodOn(getClass()).searchProducts(terms)).withSelfRel());

        return new ResponseEntity<>(jsonProducts, HttpStatus.OK);
    }

    @RequestMapping("/api/product/all")
    @ResponseBody
    public HttpEntity<JsonProducts> allProducts() {
        List<Product> products = repository.findAll();

        JsonProducts jsonProducts = jsonProducts(products);
        jsonProducts.add(linkTo(methodOn(getClass()).allProducts()).withSelfRel());

        return new ResponseEntity<>(jsonProducts, HttpStatus.OK);
    }

}
