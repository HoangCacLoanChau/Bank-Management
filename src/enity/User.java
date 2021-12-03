package enity;

import java.io.Serializable;


// giup account su dung dc writeObject
public class User implements Serializable {
    public String ID;
    public String password;

   
    public User() {
    }

  

     
    public User(String iD, String password) {
        ID = iD;
        this.password = password;
    }

 
    public String getID() {
        return ID;
    }

   
    public void setID(String iD) {
        ID = iD;
    }

    
    public String getPassword() {
        return password;
    }

 
    public void setPassword(String password) {
        this.password = password;
    }

    
    public User(String iD) {
        ID = iD;
    }

 

    
}
