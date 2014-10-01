package test.support;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

import java.net.InetSocketAddress;

public class FakeMongoServer {

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
        new FakeMongoServer().start("localhost", 27017);
    }
}
