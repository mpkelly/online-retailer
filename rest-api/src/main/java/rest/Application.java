package rest;

import com.mongodb.MongoClient;
import db.DbConfiguration;
import db.customer.CustomerRepository;
import db.product.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.config.EnableHypermediaSupport;

import java.net.UnknownHostException;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

@ComponentScan
@EnableHypermediaSupport(type= {HAL})
@EnableAutoConfiguration
public class Application implements DbConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private final MongoClient client;

    public Application() throws UnknownHostException {
        this.client = new MongoClient(DB_HOST, DB_PORT);
    }

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepository(client.getDB(DB_NAME));
    }

    @Bean
    public CustomerRepository customerRepository() {
        return new CustomerRepository(client.getDB(DB_NAME));
    }
}
