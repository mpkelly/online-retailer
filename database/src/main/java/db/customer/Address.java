package db.customer;

import java.util.Map;

public class Address {

    public static final String NUMBER = "number";
    public static final String STREET = "street";
    public static final String CITY = "city";
    public static final String POSTCODE = "postcode";

    private final String number;
    private final String street;
    private final String city;
    private final String postcode;

    Address(Map<String, Object> address) {
        this.number = address.get(NUMBER).toString();
        this.street = address.get(STREET).toString();
        this.city = address.get(CITY).toString();
        this.postcode = address.get(POSTCODE).toString();
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

