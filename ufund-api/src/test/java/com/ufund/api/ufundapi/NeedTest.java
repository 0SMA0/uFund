package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.Need;

@Tag ("Model Tier")
public class NeedTest {
    
    @Test
    public void testCtor() {
        Need expected = new Need("Canned Food", 30, 5, "Food");
    
        assertTrue(expected instanceof Need);
    }

    @Test
    public void testNeeds() {
        Need need = new Need("Canned Food", 30, 5, "Food");
        
        String expected1 = "Canned Food";
        String actual1 = need.getName();
        assertSame(expected1, actual1);
        
        int expected2 = 30;
        int actual2 = need.getCost();
        assertSame(expected2, actual2);
        
        int expected3 = 5;
        int actual3 = need.getQuantity();
        assertSame(expected3, actual3);
        
        String expected4 = "Food";
        String actual4 = need.getType();
        assertSame(expected4, actual4);
        
        String expected5 = "Blankets";
        need.setName("Blankets");
        String actual5 = need.getName();
        assertEquals(expected5, actual5);
        
        int expected6 = 40;
        need.setCost(40);
        int actual6 = need.getCost();
        assertEquals(expected6, actual6);
        
        int expected7 = 10;
        need.setQuantity(10);
        int actual7 = need.getQuantity();
        assertEquals(expected7, actual7);
        
        String expected8 = "Bedding";
        need.setType("Bedding");
        String actual8 = need.getType();
        assertEquals(expected8, actual8);
    }

    @Test
    public void testtoString() {
        String expected = new Need("Canned Food", 30, 5, "Food").toString();
        String actual = "Name: Canned Food Cost: $30 Quantity: 5 Type: Food";
        
        assertEquals(expected, actual, "Poop");
    }

    @Test
    public void testEqualsTrue(){
        boolean expected1 = true;
        Need need1 = new Need("Canned Food", 30, 5, "Food");
        Need need2 = new Need("Canned Food", 30, 5, "Food");
        boolean actual1 = need1.equals(need2);
        assertEquals(actual1, expected1);
    }

    @Test
    public void testEqualsFalse(){
        boolean expected = false;
        Need need1 = new Need("Canned Fart", 29, 5, "Food");
        Need need2 = new Need("Canned Food", 29, 5, "Food");
        boolean actual2 = need1.equals(need2);
        assertEquals(actual2, expected); 
    }

    @Test 
    public void testEqualsInvalid(){
        boolean expected = false;
        Need need = new Need("Canned Food", 29, 5, "Food");
        Object object = new Object();
        boolean actual = need.equals(object);
        assertEquals(expected, actual);
    }
}
