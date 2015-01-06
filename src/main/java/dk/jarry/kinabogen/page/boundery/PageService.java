package dk.jarry.kinabogen.page.boundery;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import dk.jarry.kinabogen.MongoClientProvider;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.BSONObject;
import org.bson.types.ObjectId;

/**
 *
 * @author Michael Bornholdt Nielsen <mni@jarry.dk>
 */
@Stateless
public class PageService {

    private final String MONGODB_DATABASE = "kinabogen";
    private final String MONGODB_COLLECTION = "page";

    /**
     * if the database should create the element if it does not exist
     */
    private final boolean UPDATE_IF_NOT_EXIST = false;

    /**
     * if the update should be applied to all objects matching (db version 1.1.3
     * and above). An object will not be inserted if it does not exist in the
     * collection and upsert=true and multi=true
     */
    private final boolean UPDATE_ALL_MATCHING_OBJECTS = false;

    private DBCollection collection;

    @Inject
    MongoClientProvider mongoClientProvider;

    public PageService() {
    }

    @PostConstruct
    public void init() {
        MongoClient mongoClient = mongoClientProvider.getMongoClient();
        DB db = mongoClient.getDB(MONGODB_DATABASE);
        collection = db.getCollection(MONGODB_COLLECTION);
    }

    public BasicDBObject create(BasicDBObject obj) {
        BasicDBObject insertObj = new BasicDBObject();
        insertObj.put("_id", new ObjectId());
        insertObj.put("created", Calendar.getInstance().getTime());
        insertObj.putAll((BSONObject) obj);
        collection.insert(insertObj, WriteConcern.SAFE);
        return insertObj;
    }

    public BasicDBObject read(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        BasicDBObject findOne = (BasicDBObject) collection.findOne(query);
        return findOne;
    }

    public BasicDBObject update(BasicDBObject dbObject) {
        String id = (String) dbObject.get("id");
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject findOne = collection.findOne(query);

        if (findOne != null) {
            collection.update(query, dbObject, UPDATE_IF_NOT_EXIST, UPDATE_ALL_MATCHING_OBJECTS,
                    WriteConcern.SAFE);
        } else {
            // TODO org not found!
            return null;
        }
        return dbObject;
    }

    public void delete(String id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject findOne = collection.findOne(query);
        collection.remove(findOne, WriteConcern.SAFE);
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public boolean isIdValid(String id, String name) {
        if (id == null) {
            return false;
        }
        try {
            new ObjectId(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
