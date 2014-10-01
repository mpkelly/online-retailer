package rest.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import db.product.Product;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;
import java.util.List;
@SuppressWarnings("unused")
public class JsonProduct extends ResourceSupport {

    private final String productId;
    private final List<String> categoryIds;
    private final String name;
    private final String description;
    private final double price;

    @JsonCreator
    @NotNull
    public JsonProduct(@JsonProperty("product_id") String productId, @JsonProperty("category_ids") List<String> categoryIds,
                       @JsonProperty("name") String name, @JsonProperty("description")String description, @JsonProperty("price")double price) {
        this.productId = productId;
        this.categoryIds = categoryIds;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public JsonProduct(Product product) {
        this(product.id(), product.categoryIds(), product.name(), product.description(), product.price());
    }

    public String getProductId() {
        return productId;
    }

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
}
