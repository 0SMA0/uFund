package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.Helper;

@Tag ("Model Tier")
public class HelperTest {
    @Test
    public void testCtor(){
        Helper actual = new Helper("Christian");

        assertTrue(actual instanceof Helper);
    }

    @Test
    public void testHelpers(){
        Helper actual = new Helper("Christian");
        String username = actual.getUsername();
        // Map<String, Need> funding_basket = actual.getFundingBasket();
        assertEquals(username, "Christian");
        // assertEquals(funding_basket, new HashMap<String, Need>());

        // Need newNeed = new Need("need1", 20, 20, "Poop");
        // actual.addToFundingBasket(newNeed);
        // Need need1 = actual.getFundingBasket().get("need1");
        // assertEquals(newNeed, need1);

        // actual.removeFromFundingBasket("need1");
        // need1 = actual.getFundingBasket().get("need1");
        // assertEquals(need1, null);
    }

    @Test
    public void testEqualsTrue(){
        boolean expected1 = true;
        Helper help1 = new Helper("Christian");
        Helper help2 = new Helper("Christian");
        boolean actual1 = help1.equals(help2);
        assertEquals(actual1, expected1);
    }

    @Test
    public void testEqualsFalse(){
        boolean expected1 = false;
        Helper help1 = new Helper("Christian");
        Helper help2 = new Helper("Ashton");
        boolean actual1 = help1.equals(help2);
        assertEquals(actual1, expected1);
    }

    @Test
    public void testEqualInvalid(){
        boolean expected1 = false;
        Helper help1 = new Helper("Christian");
        Object help2 = new Object();
        boolean actual1 = help1.equals(help2);
        assertEquals(actual1, expected1);
    }
}
