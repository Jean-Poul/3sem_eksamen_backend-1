package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.NextLaunchDTO;
import facades.NextLaunchFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    public String getPersonCount() {
        long count = FACADE.getLaunchCount();
        return "{\"count\":" + count + "}";
    }
    
    @Path("/upcoming")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getNextLaunches() {
        NextLaunchDTO Nl = FACADE.getNextLaunch();
        return GSON.toJson(Nl);        
    }
        
    
}
