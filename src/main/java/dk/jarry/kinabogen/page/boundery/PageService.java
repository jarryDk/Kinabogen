package dk.jarry.kinabogen.page.boundery;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import dk.jarry.kinabogen.MongoClientProvider;
import java.util.Calendar;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.types.ObjectId;

/**
 *
 * @author Michael Bornholdt Nielsen <mni@jarry.dk>
 */
@Stateless
public class PageService {

    private final String MONGODB_DATABASE = "kinabogen";
    private final String MONGODB_COLLECTION = "page";

    @Inject
    MongoClientProvider mongoClientProvider;

    public DBObject create(DBObject dbObject) {

        MongoClient mongoClient = mongoClientProvider.getMongoClient();
        DB db = mongoClient.getDB(MONGODB_DATABASE);
        DBCollection collection = db.getCollection(MONGODB_COLLECTION);

        dbObject.put("created", Calendar.getInstance().getTime());

        /**
         * persister dbObject in MongoDB
         */
        collection.insert(dbObject, WriteConcern.SAFE);

        return dbObject;
    }

    public DBObject read(String id) {

        MongoClient mongoClient = mongoClientProvider.getMongoClient();
        DB db = mongoClient.getDB(MONGODB_DATABASE);
        DBCollection collection = db.getCollection(MONGODB_COLLECTION);
        
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        DBObject findOne = collection.findOne(query);

        return findOne;
    }

    public DBObject update(DBObject dbObject) {

        MongoClient mongoClient = mongoClientProvider.getMongoClient();
        DB db = mongoClient.getDB(MONGODB_DATABASE);
        DBCollection collection = db.getCollection(MONGODB_COLLECTION);

        String id = (String) dbObject.get("id");

        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        DBObject findOne = collection.findOne(query);

        /**
         * if the database should create the element if it does not exist
         */
        boolean upsert = false;

        /**
         * if the update should be applied to all objects matching (db version
         * 1.1.3 and above). An object will not be inserted if it does not exist
         * in the collection and upsert=true and multi=true
         */
        boolean multi = false;

        if (findOne != null) {
            collection.update(query, dbObject, upsert, multi,
                    WriteConcern.SAFE);
        } else {
            // TODO org not found!
            return null;
        }

        return dbObject;
    }

    public void delete(String id) {

        MongoClient mongoClient = mongoClientProvider.getMongoClient();
        DB db = mongoClient.getDB(MONGODB_DATABASE);
        DBCollection collection = db.getCollection(MONGODB_COLLECTION);

        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));

        DBObject findOne = collection.findOne(query);

        collection.remove(findOne, WriteConcern.SAFE);
    }
}
