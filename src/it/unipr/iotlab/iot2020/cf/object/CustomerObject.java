package it.unipr.iotlab.iot2020.cf.object;

import java.util.ArrayList;

public class CustomerObject {

    private int id;
    private ArrayList<String> shoppingList;
    private String type;

    private String currentDepartment;

    /****************************************
     * Constructor
     ****************************************/

    public CustomerObject(){}

    public CustomerObject(int id, ArrayList<String> shoppingList) {
        this.id = id;
        this.shoppingList = shoppingList;
        this.type = "CUSTOMER";
        this.currentDepartment = null;
    }

    // public CustomerObject(String name, int peopleCounter){
    //     this.name = name;
    //     this.peopleCounter = peopleCounter;
    // }

    // public CustomerObject (int ID ,String name, int lowerBound, int upperBound) {
    //     this.name = name;
    //     this.lowerBound = lowerBound;
    //     this.upperBound = upperBound;
    // }

    /****************************************
     * Getter/Setter Method
     ****************************************/
    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public ArrayList<String> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(final ArrayList<String> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public String getType(){
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getCurrentDeparment(){
        return currentDepartment;
    }

    public void setCurrentDepartment(final String currentDepartment){
        this.currentDepartment = currentDepartment;
    }

    public String toString(){
        ArrayList<String> list = getShoppingList();
        String str = "";
        for (String l : list){
            str = str + " " + l;
        }

        return getId() + " " + str + " " + getType();
    }
}