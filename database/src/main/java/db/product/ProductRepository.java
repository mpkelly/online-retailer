package db.product;

import com.mongodb.*;
import db.AbstractRepository;

import java.util.List;
import java.util.regex.Pattern;

import static db.product.Product.*;
import static java.util.regex.Pattern.CASE_INSENSITIVE;

public class ProductRepository extends AbstractRepository<Product> {

    public ProductRepository(DB database) {
        super(database.getCollection("product"));
    }

    public List<Product> findByName(String name) {
        return cursorToList(collection.find(new BasicDBObject(NAME, name)));
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

    @Override
    protected Product create(BasicDBObject document) {
        return new Product(document);
    }
}
