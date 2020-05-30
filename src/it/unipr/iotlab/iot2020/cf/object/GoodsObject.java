package it.unipr.iotlab.iot2020.cf.object;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class GoodsObject {

    private static final AtomicInteger count = new AtomicInteger(0);
    
    private int id;
    private String name;
    private String department;
    private BigDecimal price;
    private String expirationDate;
    private int quantity;
    private int currentQuantity;
    private String type;

    /****************************************
     * Constructor                          *
     ****************************************/

    public GoodsObject() {}

    public GoodsObject(String name, String department, BigDecimal price, String expirationDate, int quantity) {
        this.id = count.incrementAndGet();
        this.name = name;
        this.department = department;
        this.price = price;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
        this.currentQuantity = quantity;
        this.type = "GOODS";
    }

    /****************************************
     * Getter/Setter Method                 *
     ****************************************/

    public void setId(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public void setPrice(final BigDecimal price) {
        this.price = price;
    } 

    public BigDecimal getPrice() {
        return price;
    }

    public void setExpirationDate(final String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setCurrentQuantity(final int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    /****************************************
     * Other Method                         *
     ****************************************/

    public String toString(){
        return getId() + " " + getName() + " " + getDepartment() + " " + getPrice() + " " + getExpirationDate() + " " + getQuantity()+ " " + getCurrentQuantity();    
    }
}