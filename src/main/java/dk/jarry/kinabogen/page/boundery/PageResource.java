package dk.jarry.kinabogen.page.boundery;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Michael Bornholdt Nielsen <mni@jarry.dk>
 */
@Path("pages")
public class PageResource {
    
    @Inject
    PageService pageService;
    
    @GET
    @Path("{id}")
    public Response read(@PathParam("id") String id) {
        return Response.accepted(pageService.read(id)).build();
    }
    
}
