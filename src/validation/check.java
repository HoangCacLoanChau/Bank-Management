package validation;
import java.util.Scanner;

import service.PasswordField;


public class check {
    private static Scanner sc = new Scanner(System.in);

    public static int getAnInteger(int lowerBound, int upperBound, String inputMsg, String errorMsg) {
        int n, tmp;       
        if (lowerBound > upperBound) {
            tmp = lowerBound;
            lowerBound = upperBound;
            upperBound = tmp;
        }       
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Integer.parseInt(sc.nextLine()); 
                if (n < lowerBound || n > upperBound)
                    throw new Exception();           
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static String getStringByPattern(String input, String error, String format){
        String str;
        boolean match;
        while(true){
            System.out.print(input);
            str = sc.nextLine().trim();
            match = str.matches(format);
            if(str.length()== 0 || str.isEmpty() || match == false)        
                System.out.println(error);  
            else
            return str;
           
        }
       
    }
    public static String getPassword(String input){
        PasswordField pw = new PasswordField();

        String password;
        String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^a-zA-Z 0-9]).{6,}$";
        do{
           
            password= pw.readPassword(input);
            if(password.length() < 5)
            System.out.println("Your password must at least 6 letters");
            else if(!password.matches(regex))
            System.out.println("Your password must include uppercase letter, "+ "lower case letters, numeric characters and 1 speacial character");

        }while(password.isEmpty()|| password.length() < 5 || !password.matches(regex));
        return password;
    }

    public static double getADouble(String input, String error){
        String tmp;
        double x;
        tmp = check.getStringByPattern(input, error, "[0-9.,]+");
        x= Double.parseDouble(tmp);
        return x;

    }

   
}
