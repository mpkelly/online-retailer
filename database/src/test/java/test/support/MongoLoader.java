package test.support;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.Map;

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
}
