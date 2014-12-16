package dk.jarry.kinabogen.page.boundery;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import dk.jarry.kinabogen.MongoClientProvider;
import java.io.File;
import java.io.FileNotFoundException;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import org.bson.types.ObjectId;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import javax.enterprise.inject.Instance;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenStrategyStage;

/**
 *
 * @author Michael Bornholdt Nielsen <mni@jarry.dk>
 */
@RunWith(Arquillian.class)
public class PageServiceTest {

//    @Inject
//    PageService pageService;
    
    @Inject
    MongoClientProvider mongoClientProvider;

    @Deployment
    public static Archive<?> createTestArchive() throws FileNotFoundException {
        
        File beans = new File("src/main/webapp/WEB-INF/beans.xml");
        
        WebArchive ret = ShrinkWrap
                .create(WebArchive.class, "KinaBogen.war")
                .addPackage(PageService.class.getPackage())
                .addPackage(MongoClientProvider.class.getPackage())
                .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml").resolve("org.mongodb:mongo-java-driver").withTransitivity().as(JavaArchive.class))
                /*.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");*/
                .addAsWebInfResource(beans, "beans.xml");
        return ret;
    }

    @Test
    public void mongo() {
        MongoClient mongoClient = mongoClientProvider.getMongoClient();
        DB db = mongoClient.getDB("kinabogen");
        DBCollection collection = db.getCollection("page");
        BasicDBObject obj = new BasicDBObject();
        obj.put("FirstClass", "Hello World");
        collection.insert(obj);

    }

//    @Test
//    public void create() {
//        BasicDBObject obj = new BasicDBObject();
//        obj.put("FirstClass", "Hello World");
//        pageService.create(obj);
//    }
}
