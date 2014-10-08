package db.customer;

import com.mongodb.BasicDBObject;
import db.Entity;

import java.util.Map;

public class Customer implements Entity {

    public static final String ID = "_id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String INVOICE_ADDRESS = "invoice_address";

    private final String id;
    private final String firstName;
    private final String lastName;
    private final Address invoiceAddress;

    @SuppressWarnings("unchecked")
    public Customer(BasicDBObject customer) {
        this.id = customer.getString(ID);
        this.firstName = customer.getString(FIRST_NAME);
        this.lastName = customer.getString(LAST_NAME);
        this.invoiceAddress = new Address((Map<String, Object>) customer.get(INVOICE_ADDRESS));
    }

    public String id() {
        return id;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public Address invoiceAddress() {
        return invoiceAddress;
    }
}
