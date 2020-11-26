
package entities;

import java.io.Serializable;

public class Geolocation implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private String locality;
    private String countryName;

    public Geolocation() {
    }

    public Geolocation(String locality, String countryName) {
        this.locality = locality;
        this.countryName = countryName;
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
