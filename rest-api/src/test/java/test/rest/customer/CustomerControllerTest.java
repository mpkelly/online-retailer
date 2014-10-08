package test.rest.customer;

import com.mongodb.BasicDBObject;
import db.customer.Customer;
import db.customer.CustomerRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.rest.AbstractControllerTest;

import java.util.HashMap;

import static db.customer.Address.*;
import static db.customer.Customer.*;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CustomerControllerTest extends AbstractControllerTest {

    @Autowired
    private CustomerRepository repository;

    private final BasicDBObject aCustomer = new BasicDBObject() {{
        append(ID, "1");
        append(FIRST_NAME, "John");
        append(LAST_NAME, "Smith");
        append(INVOICE_ADDRESS, new HashMap<String, Object>() {{
                put(NUMBER, "6a");
                put(STREET, "The street");
                put(CITY, "The city");
                put(POSTCODE, "post code");
            }}
        );
    }};

    @Test
    public void lists_correct_customer_api() throws Exception {
        mvc.perform(get("/api/customer")
                .accept(HAL_JSON))

            .andExpect(content().contentType("application/hal+json"))
            .andExpect(jsonPath("_links.self.href").value("http://localhost/api/customer"))
            .andExpect(jsonPath("_links.find.href").value("http://localhost/api/customer/:id"))
            .andExpect(jsonPath("_links.create.href").value("http://localhost/api/customer"))
            .andExpect(jsonPath("_links.browse.href").value("http://localhost/api/customer/browse?pageSize=50&pageNumber=1"))
            .andExpect(status().isOk());
    }

    @Test
    public void can_find_a_customer_by_id() throws Exception {
        String id = "1";

        when(repository.findById(id))
            .thenReturn(new Customer(aCustomer));

        mvc.perform(get("/api/customer/{id}", id)
            .accept(HAL_JSON))

            .andExpect(status().isOk())
            .andExpect(content().contentType("application/hal+json"))
            .andExpect(jsonPath("first_name").value("John"))
            .andExpect(jsonPath("last_name").value("Smith"))
            .andExpect(jsonPath("$invoice_address.number").value("6a"))
            .andExpect(jsonPath("$invoice_address.street").value("The street"))
            .andExpect(jsonPath("$invoice_address.city").value("The city"))
            .andExpect(jsonPath("$invoice_address.postcode").value("post code"))
            .andExpect(jsonPath("$_links.self.href").value("http://localhost/api/customer/" + id));
    }


    @Test
    public void can_browse_customers_page_by_page() throws Exception {
        when(repository.count())
            .thenReturn(2L);
        when(repository.browse(1, 1))
            .thenReturn(asList(new Customer(new BasicDBObject(aCustomer))));

        mvc.perform(get("/api/customer/browse?pageSize=1&pageNumber=1")
            .accept(HAL_JSON))

            .andExpect(status().isOk())
            .andExpect(content().contentType("application/hal+json"))
            .andExpect(jsonPath("items", hasSize(1)))
            .andExpect(jsonPath("$_links.self.href").value("http://localhost/api/customer/browse?pageSize=1&pageNumber=1"))
            .andExpect(jsonPath("$_links.firstPage.href").value("http://localhost/api/customer/browse?pageSize=1&pageNumber=1"))
            .andExpect(jsonPath("$_links.lastPage.href").value("http://localhost/api/customer/browse?pageSize=1&pageNumber=2"));
    }
}
