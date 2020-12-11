package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.NextLaunchDTO;
import errorhandling.NoConnectionException;
import facades.NextLaunchFacade;
import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;

@Path("nextlaunch")
public class NextLaunchResource {  
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final NextLaunchFacade FACADE = NextLaunchFacade.getNextLaunchFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"You're headed the right way!\"}";
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonCount() throws NoConnectionException {

        long count = FACADE.getLaunchCount();
        return "{\"count\":" + count + "}";
    }
    
    @Path("/upcoming")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getNextLaunches() throws IOException,NoConnectionException {
        NextLaunchDTO Nl = FACADE.getNextLaunch();
        return Response.ok(Nl.getData()).build();
    }
}
