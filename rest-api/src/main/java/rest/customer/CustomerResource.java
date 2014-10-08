package rest.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

@SuppressWarnings("unused")
public class CustomerResource extends ResourceSupport {

    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("invoice_address")
    private CustomerAddressResource invoiceAddress;

    public CustomerResource() {

    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public CustomerAddressResource getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(CustomerAddressResource address) {
        this.invoiceAddress = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public static class CustomerAddressResource {

        private String number;
        private String street;
        private String city;
        private String postcode;

        public CustomerAddressResource() {

        }

        public void setNumber(String number) {
            this.number = number;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getPostcode() {
            return postcode;
        }

        public String getNumber() {
            return number;
        }

        public String getStreet() {
            return street;
        }

        public String getCity() {
            return city;
        }
    }
}
