package dk.jarry.kinabogen.page.boundery;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import dk.jarry.kinabogen.MongoClientProvider;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Michael Bornholdt Nielsen <mni@jarry.dk>
 */
@Stateless
public class PageService {

    @Inject
    MongoClientProvider mongoClientProvider;

    public DBObject read(String id) {

        MongoClient mongoClient = mongoClientProvider.getMongoClient();

        DB db = mongoClient.getDB("kinabogen");

        return null;
    }
}
