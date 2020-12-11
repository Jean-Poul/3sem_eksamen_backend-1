package rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("geoloc")
public class ReverseGeolocationResource {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoGeo() {
        return "{\"msg\":\"Hello to geolocation\"}";
    }

    @Path("/{lat}/{lon}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getGeolocation(@PathParam("lat") String lat, @PathParam("lon") String lon) throws IOException {
        String myUrl = "https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=" + lat + "&longitude=" + lon + "&localityLanguage=en";
        URL geoloc = new URL(myUrl);
        URLConnection yc = geoloc.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream()));
        String inputLine;
        String geolocation = "";
        while ((inputLine = in.readLine()) != null) {
            geolocation += inputLine;
        }
        in.close();
        return geolocation;
    }

}
