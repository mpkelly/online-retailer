package rest.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import db.product.Product;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class JsonProducts extends ResourceSupport {

    private final List<JsonProduct> products;

    static JsonProducts jsonProducts(List<Product> products) {
        List<JsonProduct> jsonProducts = new ArrayList<>();

        for (Product product : products) {
            jsonProducts.add(new JsonProduct(product));
        }
        return new JsonProducts(jsonProducts);
    }

    @JsonCreator
    @NotNull
    public JsonProducts(@JsonProperty("products") List<JsonProduct> products) {
        this.products = products;
    }

    public List<JsonProduct> getProducts() {
        return products;
    }
}
