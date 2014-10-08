package test.support;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import db.DbConfiguration;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import static db.customer.Address.*;
import static db.customer.Customer.*;

public class MongoLoader {

    private MongoClient client;

    public MongoLoader() {
    }

    public void start(String hostname, int port) throws UnknownHostException {
        this.client = new MongoClient(hostname, port);
    }

    public void stop() {
        client.close();
    }

    public String insertDocument(String databaseName, String collectionName, Map<String, Object> fields) {
        DBObject document = new BasicDBObject(fields);

        client.getDB(databaseName)
            .getCollection(collectionName)
            .insert(document);

        return document.get("_id").toString();
    }

    public static void main(String[] args) throws UnknownHostException {
        final Map<String, Object> CUSTOMER_1 = new HashMap<String, Object>() {{
            put(FIRST_NAME, "John");
            put(LAST_NAME, "Smith");
            put(INVOICE_ADDRESS, new HashMap<String, Object>() {{
                        put(NUMBER, "6a");
                        put(STREET, "The street");
                        put(CITY, "The city");
                        put(POSTCODE, "post code");
                    }}
            );
        }};
        MongoLoader loader = new MongoLoader();
        loader.start(DbConfiguration.DB_HOST, DbConfiguration.DB_PORT);
        String id = loader.insertDocument("testdb", "customer", CUSTOMER_1);
        System.out.println(id);
        loader.stop();
    }
}
