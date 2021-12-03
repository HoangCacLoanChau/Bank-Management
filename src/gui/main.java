package gui;


import service.UserList;
import validation.check;

public class main {
    public static void main(String[] args) {
        Menu mainMenu = new Menu("BANK MANAGER\n");
        boolean loaded = false;
        mainMenu.addNewOption("1. Create new account. ");
        mainMenu.addNewOption("2. Login function.");
        mainMenu.addNewOption("3. Withdrawal function.");
        mainMenu.addNewOption("4. Deposit function.");
        mainMenu.addNewOption("5. Transfer money");
        mainMenu.addNewOption("6. Remove account.");
        mainMenu.addNewOption("7. change password");
        mainMenu.addNewOption("8. Print history");
        mainMenu.addNewOption("9.Log out");
        mainMenu.addNewOption("10. Quit");
        int choice;
        String confirmcode = null;
        UserList folder = new UserList();

        do {
            while (loaded == false) {
                folder.loadData();
                loaded = true;
                break;
            }

            mainMenu.printMenu();
            choice = mainMenu.getChoice();
            switch (choice) {
            case 1:
                folder.createNewAccount();

                confirmcode = check
                        .getStringByPattern("Do you want to continue  or quit ( Choose Y to continue or N to quit):  ",
                                "Y or N only", "[y|Yn|N]")
                        .toUpperCase();
                break;

            case 2:
                folder.loginAccount();
                confirmcode = check
                        .getStringByPattern("Do you want to continue  or quit ( Choose Y to continue or N to quit):  ",
                                "Y or N only", "[y|Yn|N]")
                        .toUpperCase();
                break;

            case 3:
                folder.withdrawMoney();
                confirmcode = check
                        .getStringByPattern("Do you want to continue  or quit ( Choose Y to continue or N to quit):  ",
                                "Y or N only", "[y|Yn|N]")
                        .toUpperCase();

                break;
            case 4:
                folder.depositMoney();
                confirmcode = check
                        .getStringByPattern("Do you want to continue or quit ( Choose Y to continue or N to quit):  ",
                                "Y or N only", "[y|Yn|N]")
                        .toUpperCase();

                break;
            case 5:
                folder.transferMoney();
                confirmcode = check
                        .getStringByPattern("Do you want to continue  or quit ( Choose Y to continue or N to quit):  ",
                                "Y or N only", "[y|Yn|N]")
                        .toUpperCase();

                break;
            case 6:
                folder.removeAccount();
                break;

            case 7:

                folder.changePassword();
                confirmcode = check
                        .getStringByPattern("Do you want to continue  or quit ( Choose Y to continue or N to quit):  ",
                                "Y or N only", "[y|Yn|N]")
                        .toUpperCase();
                break;
            case 8:
                folder.printLog();
                confirmcode = check
                        .getStringByPattern("Do you want to continue  or quit ( Choose Y to continue or N to quit):  ",
                                "Y or N only", "[y|Yn|N]")
                        .toUpperCase();

                break;
                case 9 :
                folder.logout();
                confirmcode = check
                .getStringByPattern("Do you want to continue  or quit ( Choose Y to continue or N to quit):  ",
                        "Y or N only", "[y|Yn|N]")
                .toUpperCase();
                break;
            case 10:
                System.out.println("----------- Thank you ----------");
                break;
            }

        } while (choice != 10 && confirmcode.equals("Y"));

    }

}
