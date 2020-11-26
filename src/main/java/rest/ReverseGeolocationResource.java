package rest;

import com.google.gson.Gson;
import dto.GeolocationDTO;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import utils.HttpUtils;

@Path("geoloc")
public class ReverseGeolocationResource {

    @Context
    private UriInfo context;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoGeo() {
        return "{\"msg\":\"Hello to geolocation\"}";
    }
    
    @Path("/{lat}/{lon}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getGeolocation(@PathParam("lat") String lat, @PathParam("lon") String lon) throws IOException {
        System.out.println("lat: " + lat + " lon: " + lon);
        Gson gson = new Gson();
        String geoloc = HttpUtils.fetchData("https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=" + lat + "&longitude=" + lon + "&localityLanguage=en");
        
        GeolocationDTO geolocDTO = gson.fromJson(geoloc, GeolocationDTO.class);
        String geolocation = gson.toJson(geolocDTO);
        
        return geolocation;
    }
}
