package com.ufund.api.ufundapi.model;
import com.fasterxml.jackson.annotation.JsonProperty;



public class Need{
    @JsonProperty("name") private String name;
    @JsonProperty("cost") private int cost;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("type") private String type;


    /**
     * @param name The name of the need
     * @param cost The cost of the need
     * @param quantity The quantity of the need
     * @param type the type of the need
     * Create a need with the name, cost, quantity, and type
     */
    public Need(@JsonProperty("name") String name, @JsonProperty("cost") int cost, @JsonProperty("quantity") int quantity, @JsonProperty("type") String type) {
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.type = type;
    }


    /**
     * @return
     * Returns the name of the need
     */
    public String getName() {
        return name;
    }


    /**
     * @param name the name of the need
     * Sets the name of the need
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return
     * Returns the cost of the need
     */
    public int getCost() {
        return cost;
    }


    /**
     * @param cost the cost of the need
     * Sets the cost of the need
     */
    public void setCost(int cost) {
        this.cost = cost;
    }


    /**
     * @return
     * Returns the quanitity of the need
     */
    public int getQuantity() {
        return quantity;
    }


    /**
     * @param quantity the quantity of the need
     * Sets the quantity of the need
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    /**
     * @return
     * Returns the type of need
     */
    public String getType() {
        return type;
    }


    /**
     * @param type the type of the need
     * Sets the type of need
     */
    public void setType(String type) {
        this.type = type;
    }
    

    /**
     * @return
     * Returns a string representation of the object
     */
    @Override
    public String toString() {
        return ("Name: " + name + " Cost: $" + cost + " Quantity: "  +  quantity + " Type: " + type);
    }

    @Override
    public boolean equals(Object anObject) {    
        if (this == anObject) {    
            return true;    
        }    
        if (anObject instanceof Need) {    
            return this.name.equals(((Need) anObject).getName()) &&
                   this.quantity == (((Need) anObject).getQuantity()) &&
                   this.cost == (((Need) anObject).getCost()) &&
                   this.type.equals(((Need) anObject).getType());
        }    
        return false;    
    }    
    @Override
    public int hashCode() {
        return this.name.hashCode() + this.quantity + this.cost + this.type.hashCode();
    }
}