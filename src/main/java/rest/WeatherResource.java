package rest;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import utils.HttpUtils;

@Path("weather")
public class WeatherResource {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoWeather() {
        return "{\"msg\":\"Hello to weather\"}";
    }
    
    @Path("/{lat}/{lon}/{key}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getGeolocation(@PathParam("lat") String lat, @PathParam("lon") String lon, @PathParam("key") String key) throws IOException {
        
        String weather = HttpUtils.fetchData("https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&units=metric&exclude=minutely,hourly,alerts&appid=" + key);
       
        return weather;
    }
}
