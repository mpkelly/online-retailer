package test.support;

import db.DbConfiguration;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

import java.net.InetSocketAddress;

public class FakeMongoServer implements DbConfiguration{

    private final MongoServer server;

    public FakeMongoServer() {
        server = new MongoServer(new MemoryBackend());
    }

    public void start(String hostname, int port) {
        server.bind(new InetSocketAddress(hostname, port));
    }

    public void stop() {
        server.shutdown();
    }

    public static void main(String[] args) {
        new FakeMongoServer().start(DB_HOST, DB_PORT);
    }
}
