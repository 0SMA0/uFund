package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Helper{
    @JsonProperty("username") private String username;

    /**
     * Creates Helper
     * 
     * @param username - String
     */
    public Helper(@JsonProperty("username") String username){
        this.username = username;
    }
    
    /**
     * Returns username of given Helper
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object anObject) {    
        if (this == anObject) {    
            return true;    
        }    
        if (anObject instanceof Helper) {    
            return this.username.equals(((Helper) anObject).getUsername());
        }    
        return false;    
    }    
    @Override
    public int hashCode() {
        return this.username.hashCode();
    }
}