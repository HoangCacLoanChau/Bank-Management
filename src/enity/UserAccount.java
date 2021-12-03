package enity;

import java.io.Serializable;

public class UserAccount implements Serializable {
     String id, accountName, password;
    double money;

    public UserAccount() {
    }

  
    public UserAccount(String accountName) {
        this.accountName = accountName;
        this.money = 0;
    }
    

    
    public UserAccount(String id, String accountName, String password, double money) {
        super();
        this.id = id;
        this.accountName = accountName;
        this.password = password;
        this.money = 0;
    }

    public UserAccount(String id, String accountName, String password) {
        this.id = id;
        this.accountName = accountName;
        this.password = password;
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

   
    public String getAccountName() {
        return accountName;
    }

    
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

   
    public void setPassword(String password) {
        this.password = password;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

  

    @Override
    public String toString() {
        return "UserAccount [accountName=" + accountName + ", id=" + id + ", money=" + money + ", password=" + password
                + "]";
    }

  
  

    

    
 
    
    
    
}
