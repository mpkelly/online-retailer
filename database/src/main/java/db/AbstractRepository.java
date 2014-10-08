package db;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractRepository<T> {

    protected final DBCollection collection;

    public AbstractRepository(DBCollection collection) {
        this.collection = collection;
    }

    public T insert(Map<String, Object> fields) {
        BasicDBObject document = new BasicDBObject(fields);
        collection.insert(document);
        return create(document);
    }

    public T findById(String id) {
        BasicDBObject document = (BasicDBObject) collection
                .findOne(new BasicDBObject("_id", new ObjectId(id)));
        return create(document);
    }

    public List<T> browse(int pageSize, int pageNumber) {
        int skipAmount = pageNumber > 0 ? ((pageNumber - 1) * pageSize) : 0;

        DBCursor cursor = collection.find()
                .skip(skipAmount)
                .limit(pageSize);

        return cursorToList(cursor);
    }

    public long count() {
        return collection.count();
    }

    protected List<T> cursorToList(DBCursor cursor) {
        List<T> customers = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                customers.add(create((BasicDBObject) cursor.next()));
            }
            return customers;
        } finally {
            cursor.close();
        }
    }

    protected abstract T create(BasicDBObject document);
}
