package dk.jarry.kinabogen.page.boundery;

import com.mongodb.DBObject;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Michael Bornholdt Nielsen <mni@jarry.dk>
 */
@Path("api/pages")
@Produces(MediaType.APPLICATION_JSON)
public class PageResource {

    @Context
    UriInfo context;

    @Inject
    PageService pageService;

    @GET
    public Response getHyberMedia() {

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

        jsonObjectBuilder.add("resource", "pages");

        JsonArrayBuilder links = Json.createArrayBuilder();

        JsonObjectBuilder link = Json.createObjectBuilder();
        link.add("rel", "self");
        link.add("href", context.getBaseUri().toString());
        links.add(link.build());

        link = Json.createObjectBuilder();
        link.add("rel", "read");
        link.add("href", context.getAbsolutePath() + "/{id}");

        links.add(link.build());

        jsonObjectBuilder.add("links", links.build());

        return Response.accepted(jsonObjectBuilder.build()).build();
    }

    @GET
    @Path("{id}")
    public Response read(@PathParam("id") String id) {
        try {
            DBObject read = pageService.read(id);
            if (read != null) {
                read.removeField("_id");
                read.put("id", id);
                return Response.accepted(read).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
