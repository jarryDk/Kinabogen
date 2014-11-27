package dk.jarry.kinabogen;

import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import com.mongodb.MongoClient;

/**
 *
 * @author Michael Bornholdt Nielsen <mni@jarry.dk>
 */
@Singleton
public class MongoClientProvider {

    private final String MONGODB_HOST = "localhost";
    private final int MONGODB_PORT = 27017;

    private MongoClient mongoClient = null;

    @Lock(LockType.READ)
    public MongoClient getMongoClient() {
        return mongoClient;
    }

    @PostConstruct
    public void init() {
        try {
            mongoClient = new MongoClient(MONGODB_HOST, MONGODB_PORT);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
