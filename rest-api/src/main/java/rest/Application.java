package rest;

import com.mongodb.MongoClient;
import db.product.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.net.UnknownHostException;

@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private final MongoClient client;

    public Application() throws UnknownHostException {
        this.client = new MongoClient("localhost", 27017);
    }

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepository(client.getDB("testdb"));
    }
}
