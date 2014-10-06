package db.customer;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.bson.types.ObjectId;

import static db.product.Product.ID;

public class CustomerRepository {

    private final DBCollection collection;

    public CustomerRepository(DB database) {
        this.collection = database.getCollection("customer");
    }

    public Customer findById(String id) {
        BasicDBObject document = (BasicDBObject) collection
                .findOne(new BasicDBObject(ID, new ObjectId(id)));
        return new Customer(document);
    }
}
