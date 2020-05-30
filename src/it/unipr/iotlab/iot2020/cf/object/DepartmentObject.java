package it.unipr.iotlab.iot2020.cf.object;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class DepartmentObject {

    private static final AtomicInteger count = new AtomicInteger(0);

    private int id;
    private String name;
    private int lowerBound;
    private int upperBound;
    private int peopleCounter;
    private String type;

    /****************************************
     * Constructor
     ****************************************/

    public DepartmentObject(){}

    public DepartmentObject(String name){
        this.name = name;
    }

    public DepartmentObject(String name, int peopleCounter){
        this.name = name;
        this.peopleCounter = peopleCounter;
    }

    public DepartmentObject (String name, int lowerBound, int upperBound) {
        this.id = count.incrementAndGet();
        this.name = name;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.type = "DEPARTMENT";
        this.peopleCounter = 0;
    }

    /****************************************
     * Getter/Setter Method
     ****************************************/
    
    public int getId(){
        return id;
    }

    public void setId(final int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(final String name){
        this.name = name;
    }

    public int getLowerBound(){
        return lowerBound;
    }

    public void setLowerBound(final int lowerBound){
        this.lowerBound = lowerBound;
    }

    public int getUpperBound(){
        return upperBound;
    }

    public void setUpperBound(final int upperBound){
        this.upperBound = upperBound;
    }

    public int getPeopleCounter(){
        return peopleCounter;
    }

    public void setPeopleCounter(final int peopleCounter){
        this.peopleCounter = peopleCounter;
    }

    public String getType(){
        return type;
    }

    public void setType(final String type){
        this.type = type;
    }

    public String toString() {
        return getId() + getName() + "\t\t" + getLowerBound() + "\t\t" + getUpperBound() + "\t\t" + getPeopleCounter();
    }

    public void printAll(ArrayList<DepartmentObject> department) {
        System.out.println("\nDepartment\tLowerBound\tUpperBound\tPeopleCounter");
        for(DepartmentObject depa : department){
            System.out.println(depa.toString());
        }
        System.out.println("\n");
    }
}