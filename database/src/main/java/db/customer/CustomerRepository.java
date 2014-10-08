package db.customer;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import db.AbstractRepository;

public class CustomerRepository extends AbstractRepository<Customer> {

    public CustomerRepository(DB database) {
        super(database.getCollection("customer"));
    }

    @Override
    protected Customer create(BasicDBObject entity) {
        return new Customer(entity);
    }
}
