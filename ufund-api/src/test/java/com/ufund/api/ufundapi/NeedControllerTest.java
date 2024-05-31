package com.ufund.api.ufundapi;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.controller.NeedController;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.CupboardDAO;
import com.ufund.api.ufundapi.persistence.CupboardFileDAO;

@Tag("Controller Tier")
public class NeedControllerTest {
    private NeedController controller;
    private CupboardDAO cupboard;
    Need[] testNeeds;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupNeedController() throws StreamReadException, DatabindException, IOException{
        mockObjectMapper = mock(ObjectMapper.class);

        testNeeds = new Need[3];
        testNeeds[2] = new Need("Cheat codes", 40, 5, "Entertainment");
        testNeeds[1] = new Need("Condoms", 1, 4, "Protection");
        testNeeds[0] = new Need("Canned Food", 30, 10, "Food");     
        
        when(mockObjectMapper.readValue(new File("doesnt_matter.txt"),Need[].class)).thenReturn(testNeeds);
        cupboard = new CupboardFileDAO("doesnt_matter.txt", mockObjectMapper);

        controller = new NeedController(cupboard);
    }

    @Test
    public void testGetNeed() throws IOException {
        ResponseEntity<Need> expected = new ResponseEntity<Need>(testNeeds[1], HttpStatus.OK);
        ResponseEntity<Need> actual = controller.getNeed("Condoms");
        assertEquals(expected, actual);
    }

    @Test
    public void testGetNeedNotFound() throws IOException {
        ResponseEntity<Need> expected = new ResponseEntity<Need>(HttpStatus.NOT_FOUND);
        ResponseEntity<Need> actual = controller.getNeed(null);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetNeedIO() throws IOException {
        String needName = "Canned Food";
        
        CupboardDAO mockCupboard = mock(CupboardFileDAO.class);
        Mockito.doThrow(new IOException()).when(mockCupboard).getNeed(needName);
        NeedController mockNeedController = new NeedController(mockCupboard);
        ResponseEntity<Need> actual = mockNeedController.getNeed(needName);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actual.getStatusCode());
    }

    @Test
    public void testGetNeeds() throws IOException{
        ResponseEntity<Need[]> expected = new ResponseEntity<Need[]>(testNeeds,HttpStatus.OK);
        ResponseEntity<Need[]> actual = controller.getNeeds();
        
        assertArrayEquals(expected.getBody(), actual.getBody());
    }

    @Test
    public void testGetNeedsIO() throws IOException {        
        CupboardDAO mockCupboard = mock(CupboardFileDAO.class);
        Mockito.doThrow(new IOException()).when(mockCupboard).getNeeds();
        NeedController mockNeedController = new NeedController(mockCupboard);
        ResponseEntity<Need[]> actual = mockNeedController.getNeeds();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actual.getStatusCode());
    }

    @Test
    public void testSearchNeeds() throws IOException { 
        Need[] expectedNeeds = new Need[2];
        expectedNeeds[1] = testNeeds[2];
        expectedNeeds[0] = testNeeds[0];
        ResponseEntity<Need[]> expected = new ResponseEntity<Need[]>(expectedNeeds, HttpStatus.OK);
        ResponseEntity<Need[]> actual = controller.searchNeeds("e");
        
        assertArrayEquals(expected.getBody(), actual.getBody());
    }

    @Test
    public void testSearchNeedIO() throws IOException {
        String needName = "Canned Food";
        
        CupboardDAO mockCupboard = mock(CupboardFileDAO.class);
        Mockito.doThrow(new IOException()).when(mockCupboard).findNeeds(needName);
        NeedController mockNeedController = new NeedController(mockCupboard);
        ResponseEntity<Need[]> actual = mockNeedController.searchNeeds(needName);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actual.getStatusCode());
    }

    @Test
    public void testCreateNeed() throws IOException {
        ResponseEntity<Need> expected = new ResponseEntity<Need>(new Need("Fries", 0, 2, "Food"), HttpStatus.OK);
        ResponseEntity<Need> actual = controller.createNeed(new Need("Fries", 0, 2, "Food"));
        assertEquals(expected, actual);
        
    }

    @Test
    public void testCreateNeedIO() throws IOException {
        String needName = "Canned Food";
        Need need = new Need(needName, 10, 10, "poop");
        
        CupboardDAO mockCupboard = mock(CupboardFileDAO.class);
        Mockito.doThrow(new IOException()).when(mockCupboard).createNeed(need);
        NeedController mockNeedController = new NeedController(mockCupboard);
        ResponseEntity<Need> actual = mockNeedController.createNeed(need);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actual.getStatusCode());
    }

    @Test
    public void testUpdateNeed() throws IOException {
        ResponseEntity<Need> expected = new ResponseEntity<Need>(testNeeds[2], HttpStatus.OK);
        ResponseEntity<Need> actual = controller.updateNeed(testNeeds[2] = new Need("Canned Food", 30, 12, "Food"));
        assertNotEquals(expected, actual);
    }
    
    @Test
    public void testUpdateNeedIO() throws IOException {
        String needName = "Canned Food";
        Need need = new Need(needName, 10, 10, "poop");
        
        CupboardDAO mockCupboard = mock(CupboardFileDAO.class);
        Mockito.doThrow(new IOException()).when(mockCupboard).updateNeed(needName, need);
        NeedController mockNeedController = new NeedController(mockCupboard);
        ResponseEntity<Need> actual = mockNeedController.updateNeed(need);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actual.getStatusCode());
    }

    @Test
    public void testDeleteNeed() throws IOException {
        ResponseEntity<Need> expected = new ResponseEntity<Need>(HttpStatus.OK);
        ResponseEntity<Need> actual = controller.deleteNeed("Canned Food");
        assertEquals(expected, actual);

    }

    @Test
    public void testDeleteNeedIO() throws IOException {
        String needName = "Canned Food";

        CupboardDAO mockCupboard = mock(CupboardFileDAO.class);
        Mockito.doThrow(new IOException()).when(mockCupboard).deleteNeed(needName);
        NeedController mockNeedController = new NeedController(mockCupboard);
        ResponseEntity<Need> actual = mockNeedController.deleteNeed(needName);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actual.getStatusCode());
    }
    
}