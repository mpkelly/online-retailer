package test.db;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import db.DbConfiguration;
import org.junit.After;
import org.junit.Before;
import test.support.FakeMongoServer;
import test.support.MongoLoader;

import java.net.UnknownHostException;
import java.util.Map;

public class AbstractRepositoryTest implements DbConfiguration {

    private final FakeMongoServer server;
    private MongoClient mongoClient;
    protected DB database;
    protected final MongoLoader mongoLoader;
    protected final String hostname;
    protected final int port;
    private final String collectionName;

    public AbstractRepositoryTest(String collectionName) {
        this (DB_HOST, 27777, collectionName);
    }

    public AbstractRepositoryTest(String hostname, int port, String collectionName) {
        this.hostname = hostname;
        this.port = port;
        this.collectionName = collectionName;
        this.server = new FakeMongoServer();
        this.mongoLoader = new MongoLoader();
    }

    @Before
    public void start() throws UnknownHostException {
        server.start(hostname, port);
        mongoLoader.start(hostname, port);
        mongoClient = new MongoClient(hostname, port);
        database = mongoClient.getDB(DB_NAME);
    }

    @After
    public void stop(){
        mongoLoader.stop();
        mongoClient.close();
        server.stop();
    }

    protected String insertDocument(Map<String, Object> fields) {
        return mongoLoader.insertDocument(database.getName(), collectionName, fields);
    }

}
