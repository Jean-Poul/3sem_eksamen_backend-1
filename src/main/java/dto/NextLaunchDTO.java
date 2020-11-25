package dto;

import entities.NextLaunch;


public class NextLaunchDTO {
 
    
    private String url;
    private String value;
    private NextLaunch nextLaunch;


    public NextLaunchDTO(){
    }
    
    public NextLaunchDTO(NextLaunch nextLaunch){
        this.value = nextLaunch.getData();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public NextLaunch getNextLaunch(){
        return nextLaunch;
    }
    
    public void setNextLaunch(NextLaunch nextLaunch){
        this.nextLaunch = nextLaunch;
    }
}
    
    
