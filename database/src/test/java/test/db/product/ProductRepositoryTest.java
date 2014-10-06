package test.db.product;

import db.product.Product;
import db.product.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import test.db.AbstractRepositoryTest;

import java.util.HashMap;
import java.util.Map;

import static db.product.Product.*;
import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertEquals;

public class ProductRepositoryTest extends AbstractRepositoryTest {

    private final Map<String, Object> PRODUCT_1 = new HashMap<String, Object>() {{
        put(CATEGORY_IDS, asList("cat_1"));
        put(NAME, "product 1");
        put(DESCRIPTION, "product 1 description");
        put(PRICE, 1.00);
    }};

    private final Map<String, Object> PRODUCT_2 = new HashMap<String, Object>() {{
        put(CATEGORY_IDS, asList("cat_2"));
        put(NAME, "product 2");
        put(DESCRIPTION, "product 2 description");
        put(PRICE, 2.00);
    }};

    private ProductRepository repository;

    public ProductRepositoryTest() {
        super("product");
    }

    @Before
    public void createCollection() {
        repository = new ProductRepository(database);
    }

    @Before
    public void insertDocuments() {
        insertDocument(PRODUCT_1);
        insertDocument(PRODUCT_2);
    }

    @Test
    public void can_load_product_by_id() {
        String id = insertDocument(PRODUCT_1);
        Product product = repository.findById(id);

        assertEquals("id", id, product.id());
        assertEquals("categories", asList("cat_1"), product.categoryIds());
        assertEquals("name", "product 1", product.name());
        assertEquals("description", "product 1 description", product.description());
        assertEquals("price", 1, product.price(), 0.0);
    }

    @Test
    public void can_find_product_by_name() {
        assertEquals("size", 1, repository.findByName("product 1").size());
    }

    @Test
    public void can_find_all_products() {
        assertEquals("size", 2, repository.findAll().size());
    }

    @Test
    public void can_find_products_within_price_range() {
        assertEquals("size", 0, repository.findByPriceRange(0, 0.99).size());
        assertEquals("size", 1, repository.findByPriceRange(0.99, 1.00).size());
        assertEquals("size", 2, repository.findByPriceRange(1.00, 2.00).size());
    }

    @Test
    public void can_find_products_by_text_search() {
        assertEquals("size", 0, repository.searchByText("text string that does not exist in database").size());
        assertEquals("size", 1, repository.searchByText("product 1 desc").size());
        assertEquals("size", 2, repository.searchByText("product").size());
    }
}
