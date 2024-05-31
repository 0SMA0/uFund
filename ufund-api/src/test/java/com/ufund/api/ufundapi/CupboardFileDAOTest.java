package com.ufund.api.ufundapi;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.CupboardFileDAO;

@Tag("Persistence Tier")
public class CupboardFileDAOTest {
    CupboardFileDAO cupboardDAO;
    Need[] testNeeds;
    ObjectMapper mockObjectMapper;
    
    @BeforeEach
    public void setupCupboardFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);

        testNeeds = new Need[3];
        testNeeds[0] = new Need("Cheat codes", 40, 5, "Entertainment");
        testNeeds[1] = new Need("Condoms", 1, 4, "Protection");
        testNeeds[2] = new Need("Canned Food", 30, 10, "Food");     
        
        when(mockObjectMapper.readValue(new File("doesnt_matter.txt"),Need[].class)).thenReturn(testNeeds);
        cupboardDAO = new CupboardFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testGetNeeds() throws IOException {
        Need[] actual = cupboardDAO.getNeeds();
        Need[] expected = new Need[3];
        expected[2] = new Need("Cheat codes", 40, 5, "Entertainment");
        expected[1] = new Need("Condoms", 1, 4, "Protection");
        expected[0] = new Need("Canned Food", 30, 10, "Food");    
        for (int i = 0; i < actual.length; i++){
            assertEquals(actual[i], expected[i]);
        }
    }

    @Test
    public void testFindNeeds() throws IOException{
        Need[] actual = cupboardDAO.findNeeds("Condo");
        Need[] expected = new Need[1];
        expected[0] = new Need("Condoms", 1, 4, "Protection");   
        assertArrayEquals(actual, expected); 
    }

    @Test
    public void testFindNeedsNoneFound() throws IOException{
        Need[] actual = cupboardDAO.findNeeds("Fart");
        Need[] expected = new Need[0];
        assertArrayEquals(actual, expected); 
    }

    @Test
    public void testGetNeed() throws IOException {
        Need expected = testNeeds[2];
        Need actual = cupboardDAO.getNeed("Canned Food");
        assertEquals(actual, expected);
    }

    @Test
    public void testGetNeedNotFound() throws IOException {
        Need expected = null;
        Need actual = cupboardDAO.getNeed("Canned Fart");
        assertEquals(actual, expected);
    }

    @Test
    public void testDeleteNeed() throws IOException {
        boolean actual = cupboardDAO.deleteNeed("Canned Food");
        boolean expected = true;
        assertEquals(actual, expected);
    }

    @Test
    public void testDeleteNeedNotFound() throws IOException {
        boolean actual = cupboardDAO.deleteNeed("Canned Farts");
        boolean expected = false;
        assertEquals(actual, expected);
    }

    @Test
    public void testCreateNeed() throws IOException {
        Need expected = new Need("Shoes", 30, 5, "Clothing");
        Need actual = cupboardDAO.createNeed(expected);
        assertEquals(actual, expected);
    }

    @Test
    public void testCreateNeedAlreadyMade() throws IOException {
        Need expected = testNeeds[0];
        Need actual = cupboardDAO.createNeed(expected);
        assertNull(actual);
    }

    @Test
    public void testUpdateNeed() throws IOException {
        Need expected1 = new Need("Canned Food", 50, 10, "Food");
        Need actual1 = new Need("Canned Fart", 50, 10, "Food");
        assertNotEquals(actual1, expected1);

        Need expected2 = new Need("Canned Food", 40, 10, "Food");
        Need actual2 = cupboardDAO.updateNeed("Canned Food", new Need("Canned Food", 40, 10, "Food"));
        assertEquals(actual2, expected2);
    }    

    @Test
    public void testUpdateNeedNotFound() throws IOException {
        Need expected1 = new Need("Canned Food", 50, 10, "Food");
        Need actual1 = new Need("Canned Fart", 50, 10, "Food");
        assertNotEquals(actual1, expected1);

        Need expected2 = null;
        Need actual2 = cupboardDAO.updateNeed("Canned Poop", new Need("Canned Food", 40, 10, "Food"));
        assertEquals(actual2, expected2);
    }    
}
