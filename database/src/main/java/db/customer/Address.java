package db.customer;

import com.mongodb.BasicDBObject;

public class Address {

    public static final String NUMBER = "number";
    public static final String STREET = "street";
    public static final String CITY = "city";
    public static final String POSTCODE = "postcode";

    private final String number;
    private final String street;
    private final String city;
    private final String postcode;

    Address(BasicDBObject address) {
        this.number = address.getString(NUMBER);
        this.street = address.getString(STREET);
        this.city = address.getString(CITY);
        this.postcode = address.getString(POSTCODE);
    }

    public String number() {
        return number;
    }

    public String street() {
        return street;
    }

    public String city() {
        return city;
    }

    public String postcode() {
        return postcode;
    }
}

