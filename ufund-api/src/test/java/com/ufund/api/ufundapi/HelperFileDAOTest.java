package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Helper;
import com.ufund.api.ufundapi.persistence.HelperFileDAO;

@Tag("Persistence Tier")
public class HelperFileDAOTest {
    HelperFileDAO helperDAO;
    Helper[] testHelpers;
    ObjectMapper mockObjectMapper;
    
    @BeforeEach
    public void setupHelperFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);

        // Need need1 = new Need("Cheat codes", 40, 5, "Entertainment");
        // Need need2 = new Need("Condoms", 1, 4, "Protection");
        // Need need3 = new Need("Canned Food", 30, 10, "Food");     

        testHelpers = new Helper[3];
        testHelpers[0] = new Helper("Christian");
        // testHelpers[0].addToFundingBasket(need1);
        testHelpers[1] = new Helper("Ashton");
        // testHelpers[1].addToFundingBasket(need2);
        testHelpers[2] = new Helper("Sam");  
        // testHelpers[2].addToFundingBasket(need3);   
        
        when(mockObjectMapper.readValue(new File("doesnt_matter.txt"),Helper[].class)).thenReturn(testHelpers);
        helperDAO = new HelperFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testGetHelpers() throws IOException {
        // Need need1 = new Need("Cheat codes", 40, 5, "Entertainment");
        // Need need2 = new Need("Condoms", 1, 4, "Protection");
        // Need need3 = new Need("Canned Food", 30, 10, "Food");   
        
        Helper[] actual = helperDAO.getHelpers();
        Helper[] expected = new Helper[3];
        expected[0] = new Helper("Christian");
        // expected[0].addToFundingBasket(need1);
        expected[1] = new Helper("Ashton");
        // expected[1].addToFundingBasket(need2);
        expected[2] = new Helper("Sam");  
        // expected[2].addToFundingBasket(need3);  
        System.out.print(actual.toString());
        System.out.print(expected.toString());
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testFindHelpers() throws IOException{
        Helper[] actual = helperDAO.findHelpers("Christian");
        Helper[] expected = new Helper[1];
        expected[0] = new Helper("Christian");  
        // expected[0].addToFundingBasket(new Need("Cheat codes", 40, 5, "Entertainment")); 
        assertArrayEquals(actual, expected); 
    }

    @Test
    public void testFindHelpersNoneFound() throws IOException{
        Helper[] actual = helperDAO.findHelpers("Fart");
        Helper[] expected = new Helper[0];
        assertArrayEquals(actual, expected); 
    }

    @Test
    public void testGetHelper() throws IOException {
        Helper expected = testHelpers[2];
        Helper actual = helperDAO.getHelper("Sam");
        assertEquals(actual, expected);
    }

    @Test
    public void testGetHelperNotFound() throws IOException {
        Helper expected = null;
        Helper actual = helperDAO.getHelper("Josh Lin");
        assertEquals(actual, expected);
    }

    @Test
    public void testDeleteHelper() throws IOException {
        boolean actual = helperDAO.deleteHelper("Christian");
        boolean expected = true;
        assertEquals(actual, expected);
    }

    @Test
    public void testDeleteHelperNotFound() throws IOException {
        boolean actual = helperDAO.deleteHelper("Carla Lopez");
        boolean expected = false;
        assertEquals(actual, expected);
    }

    @Test
    public void testCreateHelper() throws IOException {
        Helper expected = new Helper("Irwin Cano");
        Helper actual = helperDAO.createHelper(expected.getUsername());
        assertEquals(actual, expected);
    }

    @Test
    public void testCreateHelperAlreadyMade() throws IOException {
        Helper expected = testHelpers[0];
        Helper actual = helperDAO.createHelper(expected.getUsername());
        assertNull(actual);
    }

    @Test
    public void testUpdateHelper() throws IOException {
        Helper expected1 = new Helper("Mya Wynn");
        Helper actual1 = new Helper("Raynard Miot");
        assertNotEquals(actual1, expected1);

        Helper expected2 = new Helper("Mya Wynn");
        // expected2.addToFundingBasket(new Need("", 0, 0, ""));
        // expected1.addToFundingBasket(new Need("", 0, 0, ""));
        Helper actual2 = helperDAO.updateHelper("Mya Wynn", expected1);
        assertEquals(actual2, expected2);
    }    

    @Test
    public void testUpdateNeedNotFound() throws IOException {
        Helper expected1 = new Helper("Mya Wynn");
        Helper actual1 = new Helper("Raynard Miot");
        assertNotEquals(actual1, expected1);

        Helper expected2 = null;
        Helper actual2 = helperDAO.updateHelper("Raynard Miot", new Helper("Fariha Khan"));
        assertEquals(actual2, expected2);
    }    
}
