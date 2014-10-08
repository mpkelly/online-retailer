package db.customer;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static db.product.Product.ID;

public class CustomerRepository {

    private final DBCollection collection;

    public CustomerRepository(DB database) {
        this.collection = database.getCollection("customer");
    }

    public Customer insert(Map<String, Object> fields) {
        BasicDBObject customer = new BasicDBObject(fields);
        collection.insert(customer);
        return new Customer(customer);
    }

    public Customer findById(String id) {
        BasicDBObject document = (BasicDBObject) collection
                .findOne(new BasicDBObject(ID, new ObjectId(id)));
        return new Customer(document);
    }

    public List<Customer> find(int pageSize, int pageNumber) {
        int skipAmount = pageNumber > 0 ? ((pageNumber - 1) * pageSize) : 0;

        DBCursor cursor = collection.find()
                .skip(skipAmount)
                .limit(pageSize);

        return cursorToList(cursor);
    }

    public long count() {
        return collection.count();
    }

    private List<Customer> cursorToList(DBCursor cursor) {
        List<Customer> customers = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                customers.add(new Customer((BasicDBObject) cursor.next()));
            }
            return customers;
        } finally {
            cursor.close();
        }
    }
}
