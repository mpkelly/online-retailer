package db.product;

import com.mongodb.BasicDBObject;

import java.util.ArrayList;
import java.util.List;

public class Product {

    public static final String ID = "_id";
    public static final String CATEGORY_IDS = "category_ids";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";

    private final String id;
    private final List<String> categoryIds;
    private final String name;
    private final String description;
    private final double price;

    @SuppressWarnings("unchecked")
    Product(BasicDBObject product) {
        this.id = product.getString(ID);
        this.categoryIds = (List<String>) product.get(CATEGORY_IDS);
        this.name = product.getString(NAME);
        this.description = product.getString(DESCRIPTION);
        this.price = product.getDouble(PRICE);
    }

    public String id() {
        return id;
    }

    public List<String> categoryIds() {
        return new ArrayList<String>(categoryIds);
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public double price() {
        return price;
    }
}
