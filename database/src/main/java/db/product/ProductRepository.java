package db.product;

import com.mongodb.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static db.product.Product.*;
import static java.util.regex.Pattern.CASE_INSENSITIVE;

public class ProductRepository {

    private final DBCollection collection;

    public ProductRepository(DB database) {
        this.collection = database.getCollection("product");
    }

    public Product insert(Map<String, Object> fields) {
        BasicDBObject product = new BasicDBObject(fields);
        collection.insert(product);
        return new Product(product);
    }

    public Product findById(String id) {
        BasicDBObject document = (BasicDBObject) collection
                .findOne(new BasicDBObject(ID, new ObjectId(id)));

        return new Product(document);
    }

    public List<Product> findByName(String name) {
        return cursorToList(collection.find(new BasicDBObject(NAME, name)));
    }

    public List<Product> findAll() {
        return cursorToList(collection.find());
    }

    public List<Product> findByPriceRange(double lower, double upper) {
        DBObject range = BasicDBObjectBuilder
            .start("$gte", lower)
            .add("$lte", upper)
            .get();

        return cursorToList(collection.find(new BasicDBObject(PRICE, range)));
    }

    public List<Product> searchByText(String text) {
        Pattern pattern = Pattern.compile(text, CASE_INSENSITIVE);

        BasicDBList terms = new BasicDBList();
        terms.add(new BasicDBObject(NAME, pattern));
        terms.add(new BasicDBObject(DESCRIPTION, pattern));

        return cursorToList(collection.find(new BasicDBObject("$or", terms)));
    }

    private List<Product> cursorToList(DBCursor cursor) {
        List<Product> products = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                products.add(new Product((BasicDBObject) cursor.next()));
            }
            return products;
        } finally {
            cursor.close();
        }
    }
}
