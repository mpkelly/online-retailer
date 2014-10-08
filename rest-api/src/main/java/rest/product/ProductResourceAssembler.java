package rest.product;

import db.product.Product;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProductResourceAssembler extends ResourceAssemblerSupport<Product, ProductResource> {

    public ProductResourceAssembler() {
        super(ProductController.class, ProductResource.class);
    }

    @Override
    public ProductResource toResource(Product product) {
        ProductResource resource = createResourceWithId(product.id(), product);

        resource.setCategoryIds(product.categoryIds());
        resource.setName(product.name());
        resource.setDescription(product.description());
        resource.setPrice(product.price());

        return resource;
    }
}
