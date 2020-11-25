package dto;

import entities.NextLaunch;
import java.time.LocalDateTime;

public class NextLaunchDTO {

    private String data;
    private NextLaunch nextLaunch;
    private long id;
    private LocalDateTime time;

    public NextLaunchDTO() {
    }

    public NextLaunchDTO(NextLaunch nextLaunch) {
        this.data = nextLaunch.getData();
    }

    public String getData() {
        return data;
    }
    
    public NextLaunch getNextLaunch(){
        return nextLaunch;
    }
    
    public void setNextLaunch(NextLaunch nextLaunch){
        this.nextLaunch = nextLaunch;
    }

}
