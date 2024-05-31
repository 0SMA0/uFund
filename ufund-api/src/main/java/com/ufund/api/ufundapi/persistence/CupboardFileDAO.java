package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.security.KeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;

/**
 * Represents a Cupboard entity
 * 
 * @author team sam and friends
 */
@Component
public class CupboardFileDAO implements CupboardDAO {
    // private static final Logger LOG = Logger.getLogger(CupboardDAO.class.getName());
    Map<String, Need> needs;
    private String filename;
    private ObjectMapper objectMapper;

    /**
     * Creates a Cupboard
     * @throws IOException
     */
    public CupboardFileDAO(@Value("${needs.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }
    
    /**
     * Generates an array of needs from the hash map
     * 
     * @return  The array of needs, may be empty
     */
    private Need[] getNeedsArray(){
        return getNeedsArray(null);
    }

    /**
     * Returns specified needs in a list format. 
     * Input null for parameter to retrieve all needs.
     * @param containsText If a needs name contains this text, retrieve that need.
     * @return All needs in a list format
     */
    private Need[] getNeedsArray(String containsText) {
        List<Need> needList = new ArrayList<Need>();
        for(String key : needs.keySet()){
            if(containsText == null || key.contains(containsText)){
                Need need = needs.get(key);
                needList.add(need);
            }
        }
        Need[] needArray = new Need[needList.size()];
        needList.toArray(needArray);
        return needArray;
    }

    private boolean load() throws IOException {
        this.needs = new HashMap<String, Need>();

        Need[] needArray = objectMapper.readValue(new File(filename), Need[].class);
        for (Need need : needArray){
            needs.put(need.getName(), need);
        }
        return true;
    }

    private boolean save() throws IOException{
        Need[] needArray = getNeedsArray();
        objectMapper.writeValue(new File(filename), needArray);
        return true;
    }

    /**
     * Returns a need given a name.
     * @param name The name of the need you want
     * @return The need
     */
    @Override
    public Need getNeed(String name) throws IOException{
        synchronized(needs){
            if(this.needs.containsKey(name)){
                return this.needs.get(name);
            }
            else{
                return null;
            }
        }
    }

    @Override
    public Need[] getNeeds() throws IOException{
        synchronized(needs){
            return getNeedsArray();
        }
    }

    @Override
    public Need[] findNeeds(String containText) throws IOException{
        synchronized(needs){
            return getNeedsArray(containText);
        }
    }

    /**
     * Adds a need to the cupboard
     * @param need The need you want to add
     * @throws KeyException When a need of that name is already in there
     */
    @Override
    public Need createNeed(Need need) throws IOException{
        synchronized(needs) {
            String key = need.getName();
            if(!(this.needs.containsKey(key))){
                this.needs.put(key, need);
                save();
                return need;
            }
            return null;
        }
        
    }

    /**
     * Updates a need from the cupboard
     * @param name The name of the need
     * @param need The new info of the need
     * @throws KeyException When the string name of the need and the name of the need object don't match
     */
    @Override
    public Need updateNeed(String name, Need need) throws IOException{
        synchronized(needs){
            if(name.equals(need.getName())){
                
                this.needs.put(name, need);
                save();
                return need;
            }
            return null;
        }
    }

    /**
     * Removes a need
     * @param name Name of the need you want to remove
     * @throws KeyException When you try to remove a need that's not there
     */
    @Override
    public boolean deleteNeed(String name) throws IOException{
        synchronized(needs){
            if(this.needs.containsKey(name)){
                this.needs.remove(name);
                return save();
            }
            else{
                return false;
            }
        }
    }
}
