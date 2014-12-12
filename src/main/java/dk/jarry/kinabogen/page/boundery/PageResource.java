package dk.jarry.kinabogen.page.boundery;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.net.URI;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
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
        link.add("action","GET");
        links.add(link.build());

        link = Json.createObjectBuilder();
        link.add("rel", "create");
        link.add("href", context.getRequestUriBuilder().build().toString());
        link.add("action","POST");
        links.add(link.build());

        link = Json.createObjectBuilder();
        link.add("rel", "read, update, delete");
        link.add("href", context.getAbsolutePath() + "/{id}");
        link.add("action","GET, PUT, DELETE");
        links.add(link.build());
        
        jsonObjectBuilder.add("links", links.build());

        return Response.accepted(jsonObjectBuilder.build()).build();
    }

    @POST
    public Response create(BasicDBObject obj) {
        try {
            BasicDBObject createdObj = pageService.create(obj);
            String id = (String) createdObj.get("_id");
            URI uri = context.getRequestUriBuilder().path(id).build();
            Response.ResponseBuilder responseBuilder = Response.created(uri);
            responseBuilder.header("X-id", id);
            return responseBuilder.build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("{id}")
    public Response read(@PathParam("id") String id) {
        try {
            if(!pageService.isIdValid(id, "id")){
                JsonObjectBuilder json = Json.createObjectBuilder();
                json.add("message", "id is not valid");
                Response.ResponseBuilder responseBuilder = Response.accepted(json);
                responseBuilder.status(Response.Status.BAD_REQUEST);
                return responseBuilder.build();
            }
            BasicDBObject read = pageService.read(id);
            if (read != null) {
                read.removeField("_id");
                read.put("id", id);
                return Response.accepted(read).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") String id, BasicDBObject obj) {
        try {
            if(!pageService.isIdValid(id, "id")){
                JsonObjectBuilder json = Json.createObjectBuilder();
                json.add("message", "id is not valid");
                Response.ResponseBuilder responseBuilder = Response.accepted(json);
                responseBuilder.status(Response.Status.BAD_REQUEST);
                return responseBuilder.build();
            }
            pageService.update(obj);
            Response.ResponseBuilder responseBuilder = Response.noContent();
            return responseBuilder.build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") String id) {
        try {
            if(!pageService.isIdValid(id, "id")){
                JsonObjectBuilder json = Json.createObjectBuilder();
                json.add("message", "id is not valid");
                Response.ResponseBuilder responseBuilder = Response.accepted(json);
                responseBuilder.status(Response.Status.BAD_REQUEST);
                return responseBuilder.build();
            }
            pageService.delete(id);
            Response.ResponseBuilder responseBuilder = Response.ok();
            return responseBuilder.build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

}
