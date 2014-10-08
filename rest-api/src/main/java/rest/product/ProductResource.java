package rest.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;
@SuppressWarnings("unused")
public class ProductResource extends ResourceSupport {

    @JsonProperty("category_ids")
    private  List<String> categoryIds;
    private  String name;
    private  String description;
    private  double price;

    public ProductResource() {

    }

    @Override
    public Link getId() {
        return super.getId();
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
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
