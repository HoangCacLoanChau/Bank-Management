package gui;
import java.util.ArrayList;

import validation.check;


public class Menu {
    private String menuTitle;
    private ArrayList<String> optionList = new ArrayList<>();
    
    public Menu(String menuTitle){
        this.menuTitle = menuTitle;
    }
    
    public void addNewOption (String newOption){
        optionList.add(newOption);
    }
    
    public void printMenu(){
        if(optionList.isEmpty()){
            System.out.println("There is no item in the menu");
            return;
        }
        System.out.println("================== BANK MANAGER ========================");
        System.out.println("Choose: ");
        for(String x : optionList)
            System.out.println(x);
    }
    public int getChoice(){
        int maxOption = optionList.size();
        String input = "Choose [1.." + maxOption + "}: ";
        String error = "You are required to choose the option 1..." +maxOption;
        return check.getAnInteger(1,maxOption,input, error);
    }
    
}