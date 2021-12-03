package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import enity.Log;
import enity.User;
import enity.UserAccount;
import validation.check;

public class UserList extends ArrayList<UserAccount> {
    public static String accountID, password, function;
    public static boolean success = false;

    ArrayList<User> list = new ArrayList<>();
    ArrayList<Log> logList = new ArrayList<>();
    PasswordField pw = new PasswordField();
    Scanner sc = new Scanner(System.in);

    /* Initialize FILE */
    String fUser = "user.dat";
    String fBank = "bank.dat";
    String fLog = "log.dat";

    public void loadFromFile(String fName) {
        try {
            File f = new File(fName);
            if (!f.exists()) {
                System.out.println("Empty list.");
                return;
            }
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream fo = new ObjectInputStream(fi);
            UserAccount userAccount;
            User user;
            Log log;
            if (fName.equalsIgnoreCase(fBank)) {
                while ((userAccount = (UserAccount) (fo.readObject())) != null) {
                    this.add(userAccount);
                }
            }
            if (fName.equalsIgnoreCase(fUser)) {
                while ((user = (User) (fo.readObject())) != null) {
                    list.add(user);
                }
            }
            if (fName.equalsIgnoreCase(fLog))
                while ((log = (Log) (fo.readObject())) != null) {
                    logList.add(log);
                }
            fi.close();
            fo.close();
        } catch (Exception e) {

        }
    }

    public void saveToFile(String fName) {
        if (this.isEmpty()) {
         
            try {
                FileOutputStream f = new FileOutputStream(fName);
                f.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        try { 
            FileOutputStream f = new FileOutputStream(fName);
            ObjectOutputStream fo = new ObjectOutputStream(f);
            if (fName.equalsIgnoreCase(fUser)) {
                for (User o : list) {
                    fo.writeObject(o);
                }
            }
            if (fName.equalsIgnoreCase(fBank)) {
                for (UserAccount o : this) {
                    fo.writeObject(o);
                }
            } if (fName.equalsIgnoreCase(fLog)){
                for (Log o : logList)
                    fo.writeObject(o);
            }
            fo.close();
            f.close();
        } catch (IOException err) {
            System.out.println(err);
        }
    }

    public void loadData() {
        try {
            loadFromFile(fBank);
            loadFromFile(fUser);
            loadFromFile(fLog);

            System.out.println("Load succesfullly");
        } catch (Exception err) {

        }
    }

    private int findID(String aID) {
        for (int i = 0; i < this.size(); i++)
            if (this.get(i).getId().equalsIgnoreCase(aID))
                return i;
        return -1;

    }

    public void createNewAccount() {
        success = false;
        String accountName;
        String rePassword;
        int position;

        ////////////// id///////////////
        do {
            accountID = check.getStringByPattern("Enter your new ID [form TPxxxxxx (x is digit)]: ",
                    "Please input right form TPxxxxxx (x is digit))!", "[T][P]\\d{6}$");
            position = findID(accountID);
            if (position >= 0) {
                System.out.println("This ID is already exist!!");
            } 
        } while (position >= 0);

        ///////////// name//////////////
        accountName = check.getStringByPattern("Enter your new Account's Name: ", "character or empty are not allowed",
                "[a-z A-Z0-9]+");
        ///////////// password//////////////////
        password = check.getPassword("Input your new passwords:");
        //// rePassword////

        while (true) {

            rePassword = pw.readPassword("\bPlease re-enter password:");
            if (rePassword.equalsIgnoreCase(password)) {
                System.out.println("create successfully");
                break;
            }

            else
                System.out.println("Password does not match! Please try again.");

        }

        // add to list
        this.add(new UserAccount(accountID, accountName, password));
        list.add(new User(accountID, password));
        // save to file
        saveToFile(fBank);
        saveToFile(fUser);

    }

    //////////// LOGIN
    public void loginAccount() {
        if(success == true){
            System.out.println("You already Log in ");
        }
        else
        System.out.print("Enter your id: ");
        accountID = sc.nextLine().trim();
        password = pw.readPassword("Enter password : ");
        for (UserAccount x : this) {
            if (x.getId().equals(accountID)) {
                if (x.getPassword().equalsIgnoreCase(password)) {
                    success = true;
                    System.out.println("\bYour current balance is: " + x.getMoney() + " VND");
                    System.out.println("Login successfully");
                    function = "Login Account"+"|Balance:"+ x.getMoney()+" VND";
                    logList.add(new Log(accountID, function));
                    saveToFile(fLog);
                    break;
                }
            }
        }
        if (success == false) {
            System.out.println("Login failed");
        }

    }
    ///////////////log out
    public void logout() {
        try {
            if (success == false) {
                System.out.println( "YOU NEED TO LOGIN FIRST" );
            } else {
                success= false;
                System.out.println("Log out successfully" );
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    /////////// withdraw///////
    public void withdrawMoney() {
        double moneyWithdraw;
        double moneyCaculator;
        if (success == true) {
            moneyWithdraw = check.getADouble("Please enter money you want withdraw: ", "only number >0 allowed !!!");
            for (UserAccount x : this) {
                if (x.getId().equals(accountID)) {
                    if (x.getMoney() >= moneyWithdraw) {
                        moneyCaculator = x.getMoney() - moneyWithdraw;
                        x.setMoney(moneyCaculator);
                        System.out.println("You have withdraw: " + moneyWithdraw + " VND" + "\nYour left balance is: "
                                + x.getMoney() + " VND");
                        function = "You have withdraw: " + moneyWithdraw + " VND"+"|Balance:"+ x.getMoney()+" VND";
                        logList.add(new Log(accountID, function));
                        saveToFile(fBank);
                        saveToFile(fLog);
                        saveToFile(fUser);
                        System.out.println("withdraw successfully");
                        break;
                    } else
                        System.out.println("You do not have enough money to withdraw ");
                    function = "You withdrawed falied";
                    logList.add(new Log(accountID, function));

                }

            }

        } else
            System.out.println("You need to login first ");

    }

    ///////////// deposit
    public void depositMoney() {
        double moneyDeposit;
        String confirm;

        if (success == true) {
            moneyDeposit = check.getADouble("Enter money you want to deposit: ", "only number > 0 allowed");

            do {
                confirm = check.getStringByPattern("Do you sure to process transaction: DEPOSIT: " + moneyDeposit
                        + " VND" + " Y/N ?" + "\nYour Choice: ", "Y or N only !!!", "[Y|yN|n]").toUpperCase();
            } while (!confirm.equals("N") && !confirm.equals("Y"));
            if (confirm.equals("Y")) {
                for (UserAccount x : this) {
                    if (x.getId().equals(accountID)) {
                        x.setMoney(x.getMoney() + moneyDeposit);
                        saveToFile(fBank);
                        System.out.println("Deposit " + moneyDeposit + " VND" + " sucessfully");
                        System.out.println("Your current balance is : " + x.getMoney() + " VND");
                        logList.add(new Log(accountID, "Deposit: " + moneyDeposit + " VND"+"|Balance:"+ x.getMoney()+" VND"));
                        saveToFile(fLog);
                        break;
                    }
                }
            }
        } else
            System.out.println("You need to log in first.");
    }

    //////////////// transfer
    public void transferMoney() {
        String receivedAccountID;
        double moneyTransfer;
        String recentID = accountID;
        double moneyAfter;
        boolean checker = false;
        if (success == true) {
            do {
                receivedAccountID = check.getStringByPattern(
                        "Enter the ID you want to transfer [form TPxxxxxx (x is digit)]: ",
                        "Please input right form TPxxxxxx (x is digit))!", "[T|t][P|p]\\d{6}$");
                if (receivedAccountID.equals(recentID)) {
                    System.out.println("You can not transfer money to your account");
                    logList.add(new Log(accountID, "transfer failed"));
                    checker = true;
                    break;
                }
                if (findID(receivedAccountID) == -1) {
                    System.out.println("Account is not existed");
                    logList.add(new Log(accountID, "transfer failed"));
                    checker = true;
                    break;
                } else {
                    moneyTransfer = check.getADouble("Enter Money you want to transfer: ", "only number >0 allowed");
                    if (moneyTransfer > this.get(findID(recentID)).getMoney()) {
                        System.out.println("your money not enough!");
                        System.out.println("Your account has " + this.get(findID(recentID)).getMoney() + " VND");
                        logList.add(new Log(accountID, "transfer failed"+"|Balance:"+ this.get(findID(accountID)).getMoney()+" VND"));
                        checker = true;
                        break;

                    }
                    String confirmCode = check.getStringByPattern(
                            "Please type Y to confirm your request and N to cancel \nYour choice: ", " Y or N only !",
                            "[y|Yn|N]");

                    if (confirmCode.equalsIgnoreCase("Y")) {
                        for (UserAccount x : this) {
                            if (x.getId().equals(accountID) && x.getMoney() >= moneyTransfer) {
                                for (UserAccount a : this) {
                                    if (a.getId().equals(receivedAccountID)) {
                                        moneyAfter = x.getMoney() - moneyTransfer;
                                        x.setMoney(moneyAfter);
                                       
                                        function = "You transfer: " + moneyTransfer + " VND to account "
                                                + receivedAccountID+"|Balance:"+ x.getMoney()+" VND";
                                        System.out.println("You transfer " + moneyTransfer + " VND to account "
                                                + receivedAccountID + " successfully");
                                                moneyTransfer += a.getMoney();
                                                a.setMoney(moneyTransfer);
                                        System.out.println("Your current balance is " + x.getMoney());
                                        logList.add(new Log(accountID, function));
                                        saveToFile(fBank);
                                        saveToFile(fUser);
                                        saveToFile(fLog);
                                        checker = true;

                                    }

                                }
                            }
                        }

                    } else if (confirmCode.equalsIgnoreCase("N")) {
                        System.out.println("Cancel request!");
                        logList.add(new Log(accountID, "transfer failed"));
                        checker = true;

                    }

                }
            } while (checker == false);

        } else
            System.out.println("Please log in first");

    }

   

    //////////////////// remove
    public void removeAccount() {
        int position = findID(accountID);
       
        boolean isSuccess = false;
        if (success == true) {

            do {
                String confirmCode = check.getStringByPattern(
                        "Please type Y to confirm your request and N to cancel \nYour choice: ", " Y or N only !",
                        "[y|Yn|N]");
                if (confirmCode.equalsIgnoreCase("Y")) {
                    this.remove(position);
                    list.remove(position);
                    logList.removeIf(logList -> logList.getAccountId().equals(accountID));
                    saveToFile(fBank);
                    saveToFile(fUser);
                    saveToFile(fLog);
                    System.out.println("Account has been removed successfully");
                    success = false;
                    isSuccess = true;
                    break;
                } else if (confirmCode.equalsIgnoreCase("N")) {
                    System.out.println("Cancel request!");
                    isSuccess = true;

                }
            } while (isSuccess == false);

        } else
            System.out.println("You need to log in first.");

    }

    /// change password
    public void changePassword() {
        if (success == true) {
            String oldPassword;
            String newPassword;
            String confirmPassword;

            int position = findID(accountID);

            do {
                /////// old pw
                oldPassword = pw.readPassword("Enter your old Password: ");
                if (oldPassword.equalsIgnoreCase(this.get(position).getPassword()) == false) {
                    System.out.println("Old password is wrong");

                }
            } while (oldPassword.equalsIgnoreCase(this.get(position).getPassword()) == false);

            /// new pw
            do {
                do {
                    newPassword = check.getPassword("Enter new Password: ");
                    if (newPassword.equalsIgnoreCase(oldPassword) == true) {
                        System.out.println("This is your old password ");
                    }
                } while (newPassword.equalsIgnoreCase(oldPassword) == true);
                /// confirm
                confirmPassword = pw.readPassword("Confirm your new Password: ");
                if (newPassword.equalsIgnoreCase(confirmPassword) == false) {
                    System.out.println("Your confirm password does not match");

                } else {
                    this.get(position).setPassword(newPassword);
                    list.get(position).setPassword(newPassword);
                    function = " Change Password";
                    logList.add(new Log(accountID, function));
                    saveToFile(fBank);
                    saveToFile(fUser);
                    saveToFile(fLog);
                    success = false;

                }
            } while (newPassword.equalsIgnoreCase(confirmPassword) == false);

        } else {
            System.out.println("You need to login first");
        }
    }

    ///// print log
    public void printLog() {
        if (success == true) {
            for (Log o : logList) {
                if (o.getAccountId().equalsIgnoreCase(accountID))
                    if (o.getCurrentMonth() == o.getLastMonth() + 1)
                        o.print();
            }
        } else
            System.out.println("You need to log in first.");
    }

    public void checkBalance() {
        int pos = findID(accountID);
        if (success == true) {
            if (this.get(pos).getMoney() == 0) {
                System.out.println("Your balance is 0 VND");
            } else {
                System.out.println("Hello " + this.get(pos).getAccountName());
                System.out.println("Your account has " + this.get(pos).getMoney() + " VND");
            }
        } else {
            System.out.println("You need to log in first");
        }
    }

}
