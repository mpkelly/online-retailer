package test.rest.product;

import com.mongodb.BasicDBObject;
import db.product.Product;
import db.product.ProductRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import test.rest.AbstractControllerTest;

import java.util.ArrayList;

import static db.product.Product.*;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static rest.util.Json.jsonToMap;

public class ProductControllerTest extends AbstractControllerTest {

    @Autowired
    private ProductRepository repository;

    static String JSON_PRODUCT = "{ \"name\" : \"Product\", \"description\" : \"Product Description\", \"category_ids\" : [\"cat\"], \"price\" : 1.0}";

    private BasicDBObject aProduct = new BasicDBObject() {{
        append(ID, "1");
        append(CATEGORY_IDS, asList("cat_1"));
        append(NAME, "a product");
        append(DESCRIPTION, "a product description");
        append(PRICE, 1.23);
    }};

    @Test
    public void lists_correct_product_api() throws Exception {
        mvc.perform(get("/api/product")
            .accept(HAL_JSON))

            .andExpect(content().contentType("application/hal+json"))
            .andExpect(jsonPath("_links.self.href").value("http://localhost/api/product"))
            .andExpect(jsonPath("_links.find.href").value("http://localhost/api/product/:id"))
            .andExpect(jsonPath("_links.create.href").value("http://localhost/api/product"))
            .andExpect(jsonPath("_links.search.href").value("http://localhost/api/product/search?terms=terms"))
            .andExpect(jsonPath("_links.browse.href").value("http://localhost/api/product/browse?pageSize=50&pageNumber=1"))
            .andExpect(status().isOk());
    }

    @Test
    public void can_find_a_product_by_id() throws Exception {
        String id = "1";

        when(repository.findById(id))
            .thenReturn(new Product(aProduct));

        mvc.perform(get("/api/product/{id}", id)
            .accept(HAL_JSON))

            .andExpect(status().isOk())
            .andExpect(content().contentType("application/hal+json"))
            .andExpect(jsonPath("$category_ids", hasSize(1)))
            .andExpect(jsonPath("$category_ids", contains("cat_1")))
            .andExpect(jsonPath("name").value("a product"))
            .andExpect(jsonPath("description").value("a product description"))
            .andExpect(jsonPath("price").value(1.23))
            .andExpect(jsonPath("$_links.self.href").value("http://localhost/api/product/" + id));
    }

    @Test
    public void can_create_a_new_product() throws Exception {
        when(repository.insert(jsonToMap(JSON_PRODUCT)))
            .thenReturn(new Product(new BasicDBObject(aProduct)));

        mvc.perform(post("/api/product/")
            .content(JSON_PRODUCT)
            .contentType(MediaType.APPLICATION_JSON))

            .andExpect(status().isCreated())
            .andExpect(header().string("Location", equalTo("http://localhost/api/product/1")));
    }

    @Test
    public void can_search_for_products() throws Exception {
        String terms = "some terms";

        when(repository.searchByText(terms))
            .thenReturn(new ArrayList<Product>());

        mvc.perform(get("/api/product/search?terms=" + terms).accept(HAL_JSON))
            .andExpect(status().isOk());

    }

    @Test
    public void can_browse_products_page_by_page() throws Exception {
        when(repository.count())
            .thenReturn(2L);
        when(repository.browse(1, 1))
            .thenReturn(asList(new Product(new BasicDBObject(aProduct))));

        mvc.perform(get("/api/product/browse?pageSize=1&pageNumber=1")
            .accept(HAL_JSON))

            .andExpect(status().isOk())
            .andExpect(content().contentType("application/hal+json"))
            .andExpect(jsonPath("products", hasSize(1)))
            .andExpect(jsonPath("$_links.self.href").value("http://localhost/api/product/browse?pageSize=1&pageNumber=1"))
            .andExpect(jsonPath("$_links.firstPage.href").value("http://localhost/api/product/browse?pageSize=1&pageNumber=1"))
            .andExpect(jsonPath("$_links.lastPage.href").value("http://localhost/api/product/browse?pageSize=1&pageNumber=2"));
    }
}
