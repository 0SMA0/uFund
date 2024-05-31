package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.FundingBasket;
import com.ufund.api.ufundapi.model.Need;

public interface FundingBasketDAO {
    FundingBasket getBasket(String username) throws IOException;
    FundingBasket[] getBaskets() throws IOException;
    FundingBasket createBasket(String username) throws IOException;
    void addNeed(FundingBasket basket, Need need) throws IOException;
    void removeNeed(FundingBasket basket, Need need) throws IOException;
    void checkOut(FundingBasket basket) throws IOException;
}
