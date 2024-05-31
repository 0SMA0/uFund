package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Helper;

@Component
public class HelperFileDAO implements HelperDAO{
    Map<String, Helper> helpers;
    private String filename;
    private ObjectMapper objectMapper;
    
    public HelperFileDAO(@Value("${helpers.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Generates an array of helpers from the hash map
     * 
     * @return  The array of helpers, may be empty
     */
    private Helper[] getHelpersArray(){
        return getHelpersArray(null);
    }

    /**
     * Returns specified helpers in a list format. 
     * Input null for parameter to retrieve all helpers.
     * @param containsText If a helpers name contains this text, retrieve that helper.
     * @return All helpers in a list format
     */
    private Helper[] getHelpersArray(String containsText) {
        List<Helper> helperList = new ArrayList<Helper>();
        for(String key : helpers.keySet()){
            if(containsText == null || key.contains(containsText)){
                Helper helper = this.helpers.get(key);
                helperList.add(helper);
            }
        }
        Helper[] helperArray = new Helper[helperList.size()];
        helperList.toArray(helperArray);
        return helperArray;
    }

    private boolean load() throws IOException {
        this.helpers = new HashMap<String, Helper>();

        Helper[] helperArray = objectMapper.readValue(new File(filename), Helper[].class);
        for (Helper helper : helperArray){
            helpers.put(helper.getUsername(), helper);
        }
        return true;
    }

    private boolean save() throws IOException{
        Helper[] helperArray = getHelpersArray();
        objectMapper.writeValue(new File(filename), helperArray);
        return true;
    }


    @Override
    public Helper getHelper(String username) throws IOException {
        synchronized(helpers){
            if(this.helpers.containsKey(username)){
                return this.helpers.get(username);
            }
            else{
                return null;
            }
        }
    }

    @Override
    public Helper[] getHelpers() throws IOException {
        synchronized(helpers){
            return getHelpersArray();
        }
    }

    @Override
    public Helper[] findHelpers(String containText) throws IOException {
        synchronized(helpers){
            return getHelpersArray(containText);
        }
    }

    @Override
    public Helper createHelper(String username) throws IOException {
        synchronized(helpers) {
            if(!(this.helpers.containsKey(username))){
                Helper helper = new Helper(username);
                this.helpers.put(username, helper);
                save();
                return helper;
            }
            return null;
        }
    }

    @Override
    public Helper updateHelper(String username, Helper helper) throws IOException {
        synchronized(helpers){
            if(username.equals(helper.getUsername())){
                this.helpers.put(username, helper);
                save();
                return helper;
            }
            return null;
        }
    }

    @Override
    public boolean deleteHelper(String username) throws IOException {
        synchronized(helpers){
            if(this.helpers.containsKey(username)){
                this.helpers.remove(username);
                return save();
            }
            else{
                return false;
            }
        }
    }
    
}
