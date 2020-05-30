package it.unipr.iotlab.iot2020.cf.object;

import java.util.concurrent.atomic.AtomicInteger;

public class ShoppingListObject {

    private static final AtomicInteger count = new AtomicInteger(0);

    private static int id;
    private static int productID;
    private static String name;
    private static String department;
    private static int quantity;

    /****************************************
     * Constructor                          *
     ****************************************/

    public ShoppingListObject(){}

    public ShoppingListObject(int productID, String name, String department, int quantity) {
        this.id = count.incrementAndGet();
        this.productID = productID;
        this.name = name;
        this.department = department;
        this.quantity = quantity;
    }

    /****************************************
     * Getter/Setter Method
     ****************************************/

    public void setId(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setProductID(final int productID) {
        this.productID = productID;
    }

    public int getproductID() {
        return productID;
    }

    public void setDepartment(final String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}