package com.ufund.api.ufundapi.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FundingBasket {
    @JsonProperty("username") private String username;
    @JsonProperty("basket") private Map<String, Need> basket;


    /**
     * @param username The username of the helper
     * Create a funding basket with needs for the user
     */
    public FundingBasket(@JsonProperty("username")String username){
        this.username = username;
        this.basket = new HashMap<>();
    }

    /**
     * @return
     * Returns the username of the whoever's funding basket this is
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * @return
     * Returns the funding basket of the user
     */
    public Map<String, Need> getBasket(){
        return this.basket;
    }

    /**
     * @param need a need object
     * Adds the need to the basket 
     */
    public void addNeed(Need need){
        this.basket.put(need.getName(), need);
    }

    /**
     * @param need a need object
     * removes the need from the basket 
     */
    public void removeNeed(Need need){
        this.basket.remove(need.getName());
    }

    /**
     * removes all needs from funding basket
     */
    public void checkOut(){
        for(String key : this.basket.keySet()){
            this.basket.remove(key);
        }
    }

    /**
     * @return
     * Returns true if the usernames are equal
     * Returns false if the usernames are not equal
     */
    @Override
    public boolean equals(Object anObject) {    
        if (this == anObject) {    
            return true;    
        }    
        if (anObject instanceof FundingBasket) {    
            return this.username.equals(((FundingBasket) anObject).getUsername());
        }    
        return false;    
    }    
    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    @Override
    public String toString(){
        String str = "";
        str += this.username;
        str += "   ";
        str += this.basket;
        return str;
    }
}
