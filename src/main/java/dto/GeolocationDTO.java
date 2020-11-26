package dto;

import entities.Geolocation;

public class GeolocationDTO {

    private String locality;
    private String countryName;

    public GeolocationDTO() {
    }

    public GeolocationDTO(Geolocation geoloc) {
        this.locality = geoloc.getLocality();
        this.countryName = geoloc.getCountryName();
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    
    
}
