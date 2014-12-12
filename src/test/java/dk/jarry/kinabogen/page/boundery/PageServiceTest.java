package dk.jarry.kinabogen.page.boundery;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
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

/**
 *
 * @author Michael Bornholdt Nielsen <mni@jarry.dk>
 */
@RunWith(Arquillian.class)
public class PageServiceTest {

//    @Inject
//    PageService pageService;

    @Deployment
    public static Archive<?> createTestArchive() throws FileNotFoundException {
        WebArchive ret = ShrinkWrap
                .create(WebArchive.class, "KinaBogen.war")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        return ret;
    }

    @Test
    public void create() {
//        BasicDBObject obj = new BasicDBObject();
//        obj.put("FirstClass", "Hello World");
//        pageService.create(obj);
    }
}
