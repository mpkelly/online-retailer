package test.db.customer;

import db.customer.Address;
import db.customer.Customer;
import db.customer.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import test.db.AbstractRepositoryTest;

import java.util.HashMap;
import java.util.Map;

import static db.customer.Address.*;
import static db.customer.Customer.*;
import static junit.framework.TestCase.assertEquals;

public class CustomerRepositoryTest extends AbstractRepositoryTest {

    private final Map<String, Object> CUSTOMER_1 = new HashMap<String, Object>() {{
        put(FIRST_NAME, "John");
        put(LAST_NAME, "Smith");
        put(INVOICE_ADDRESS, new HashMap<String, Object>() {{
                put(NUMBER, "6a");
                put(STREET, "The street");
                put(CITY, "The city");
                put(POSTCODE, "post code");
            }}
        );
    }};

    private CustomerRepository repository;

    public CustomerRepositoryTest() {
        super("customer");
    }

    @Before
    public void createCollection() {
        repository = new CustomerRepository(database);
    }

    @Test
    public void can_load_customer_by_id() {
        String id = insertDocument(CUSTOMER_1);

        Customer customer = repository.findById(id);

        assertEquals("id", id, customer.id());
        assertEquals("first name", "John", customer.firstName());
        assertEquals("last name", "Smith", customer.lastName());

        Address invoiceAddress = customer.invoiceAddress();
        assertEquals("number", "6a", invoiceAddress.number());
        assertEquals("street", "The street", invoiceAddress.street());
        assertEquals("city", "The city", invoiceAddress.city());
        assertEquals("postcode", "post code", invoiceAddress.postcode());
    }
}
