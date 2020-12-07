
package dto;

import entities.User;


public class UserDTO {
    
    private String userID;
    private String password;

    public UserDTO() {
    }

    public UserDTO(User u) {
        this.userID = u.getUserName();
        this.password = u.getUserPass();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
      
}
