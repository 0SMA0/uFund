package com.ufund.api.ufundapi;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.FundingBasket;
import com.ufund.api.ufundapi.model.Need;

@Tag("Model Tier")
public class FundingBasketTest {
    @Test
    public void testCtor(){
        FundingBasket actual = new FundingBasket("Christian");
        assertTrue(actual instanceof FundingBasket);
    }

    @Test
    public void testGetUsername() {
        FundingBasket actual = new FundingBasket("Christian");
        String username = actual.getUsername();
        assertEquals(username, "Christian");
    }

     @Test
     public void testGetBasket() {
        FundingBasket actual = new FundingBasket("Christian");
        Map<String, Need> basket = actual.getBasket();
        basket.put("Christian", new Need("name", 1, 1, "type"));
        assertFalse(basket.isEmpty());
    }

    @Test
    public void testAddNeed() {
        FundingBasket actual = new FundingBasket("Christian");
        Need test = new Need("name", 1, 1, "type");
        actual.addNeed(test);
        assertEquals(test, actual.getBasket().get("name"));
    }

    @Test
    public void testRemoveNeed() {
        FundingBasket actual = new FundingBasket("Christian");
        Need test = new Need("name", 1, 1, "type");
        actual.addNeed(test);
        actual.removeNeed(test);
        assertTrue(actual.getBasket().isEmpty());
    }

    @Test
    public void testCheckOut() {
        FundingBasket actual = new FundingBasket("Christian");
        Need test = new Need("name", 1, 1, "type");
        actual.addNeed(test);
        actual.checkOut();
        assertTrue(actual.getBasket().isEmpty());
    }

    @Test
    public void testEqualsTrue(){
        boolean expected = true;
        FundingBasket basket1 = new FundingBasket("Christian");
        FundingBasket basket2 = new FundingBasket("Christian");
        boolean actual = basket1.equals(basket2);
        assertEquals(actual, expected);
    }

    @Test
    public void testEqualsFalse(){
        boolean expected = false;
        FundingBasket basket1 = new FundingBasket("Ashton");
        FundingBasket basket2 = new FundingBasket("Christian");
        boolean actual = basket1.equals(basket2);
        assertEquals(actual, expected);
    }

    @Test
    public void testEqualsInvalid(){
        boolean expected = false;
        Object basket1 = new Object();
        FundingBasket basket2 = new FundingBasket("Christian");
        boolean actual = basket1.equals(basket2);
        assertEquals(actual, expected);
    }
    @Test 
    void testHashCodeTrue() {
        FundingBasket basket1 = new FundingBasket("Christian");
        FundingBasket basket2 = new FundingBasket("Christian");
        assertEquals(basket1.hashCode(), basket2.hashCode());
    }

    @Test
    void testHashCodeFalse() {
        FundingBasket basket1 = new FundingBasket("Christian");
        FundingBasket basket2 = new FundingBasket("Ashton");
        assertFalse(basket1.hashCode() == basket2.hashCode());
    }

    @Test
    public void testToString() {
        FundingBasket testBasket = new FundingBasket("testUser");
        Need need1 = new Need("item1", 10, 2, "type1");
        testBasket.addNeed(need1);

        String expected = "testUser   {item1=Name: item1 Cost: $10 Quantity: 2 Type: type1}";
        assertEquals(expected, testBasket.toString());
    }


}
