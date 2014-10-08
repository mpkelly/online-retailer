package test.rest;

import db.customer.CustomerRepository;
import db.product.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfiguration {

    @Bean
    public ProductRepository productRepository() {
        return mock(ProductRepository.class);
    }

    @Bean
    public CustomerRepository customerRepository() {
        return mock(CustomerRepository.class);
    }
}
