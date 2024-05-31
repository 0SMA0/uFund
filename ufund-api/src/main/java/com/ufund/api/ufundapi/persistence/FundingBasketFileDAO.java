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
import com.ufund.api.ufundapi.model.FundingBasket;
import com.ufund.api.ufundapi.model.Need;

@Component
public class FundingBasketFileDAO implements FundingBasketDAO {

    Map<String, FundingBasket> baskets;
    private String filename;
    private ObjectMapper objectMapper;

    public FundingBasketFileDAO(@Value("${baskets.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Generates an array of baskets from the hash map
     * 
     * @return  The array of baskets, may be empty
     */
    private FundingBasket[] getBasketsArray(){
        return getBasketsArray(null);
    }

    /**
     * Returns specified baskets in a list format. 
     * Input null for parameter to retrieve all baskets.
     * @param containsText If a baskets name contains this text, retrieve that basket.
     * @return All baskets in a list format
     */
    private FundingBasket[] getBasketsArray(String containsText) {
        List<FundingBasket> basketList = new ArrayList<FundingBasket>();
        for(String key : baskets.keySet()){
            if(containsText == null || key.contains(containsText)){
                FundingBasket basket = this.baskets.get(key);
                basketList.add(basket);
            }
        }
        FundingBasket[] basketArray = new FundingBasket[basketList.size()];
        basketList.toArray(basketArray);
        return basketArray;
    }

    private boolean load() throws IOException {
        this.baskets = new HashMap<String, FundingBasket>();

        FundingBasket[] basketArray = objectMapper.readValue(new File(filename), FundingBasket[].class);
        for (FundingBasket basket : basketArray){
            baskets.put(basket.getUsername(), basket);
        }
        return true;
    }

    private boolean save() throws IOException{
        FundingBasket[] basketArray = getBasketsArray();
        objectMapper.writeValue(new File(filename), basketArray);
        return true;
    }

    @Override
    public FundingBasket getBasket(String username) {
        synchronized(baskets){
            if(this.baskets.containsKey(username)){
                return this.baskets.get(username);
            }
            else{
                return null;
            }
        }
    }
    
    @Override
    public FundingBasket[] getBaskets() {
        synchronized(baskets){
            return getBasketsArray();
        }
    }

    @Override
    public void addNeed(FundingBasket basket, Need need) throws IOException {
        synchronized(baskets){
            String username = basket.getUsername();
            if(this.baskets.containsKey(username)){
                FundingBasket fundingBasket = this.baskets.get(username);
                fundingBasket.addNeed(need);
                save();
            }
        }
    }

    @Override
    public void removeNeed(FundingBasket basket, Need need) throws IOException {
        synchronized(baskets){
            String username = basket.getUsername();
            if(this.baskets.containsKey(username)){
                FundingBasket fundingBasket = this.baskets.get(username);
                fundingBasket.removeNeed(need);
                save();
            }
        }
    }

    @Override
    public void checkOut(FundingBasket basket) throws IOException {
        synchronized(baskets){
            String username = basket.getUsername();
            if(this.baskets.containsKey(username)){
                FundingBasket fundingBasket = this.baskets.get(username);
                fundingBasket.checkOut();
                save();
            }
        }
    }

    @Override
    public FundingBasket createBasket(String username) throws IOException {
        synchronized(baskets){
            if(!(this.baskets.containsKey(username))){
                FundingBasket basket = new FundingBasket(username);
                this.baskets.put(username, basket);
                save();
                return basket;
            }
            return null;
        }
    }
}
