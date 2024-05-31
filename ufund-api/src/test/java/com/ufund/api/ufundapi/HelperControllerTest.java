package com.ufund.api.ufundapi;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufund.api.ufundapi.controller.HelperController;
import com.ufund.api.ufundapi.model.Helper;
import com.ufund.api.ufundapi.persistence.HelperDAO;




@Tag("Controller Tier")
public class HelperControllerTest {
    private HelperDAO helper;
    private HelperController controller;
Helper[] testHelpers;

    @BeforeEach
    void setupHelperController() {
        helper = mock(HelperDAO.class);
        controller = new HelperController(helper);

        testHelpers = new Helper[3];
        testHelpers[0] = new Helper("user1");
        testHelpers[1] = new Helper("user2");
        testHelpers[2] = new Helper("user3");
    }

    @Test
    void testGetHelper() throws IOException {
        String username = "user";
        Helper expected = new Helper(username);
        when(helper.getHelper(username)).thenReturn(expected);

        ResponseEntity<Helper> response = controller.getHelper(username);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }
    
    @Test
    void testGetHelperIo() throws IOException {
        String username = "test";
        helper = mock(HelperDAO.class);
        when(helper.getHelper(username)).thenThrow(new IOException("IOException"));
        HelperController helperController = new HelperController(helper);

        ResponseEntity<Helper> actualResponse = helperController.getHelper(username);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
    }

    @Test
    void testGetHelpers() throws IOException {
        when(helper.getHelpers()).thenReturn(testHelpers);

        ResponseEntity<Helper[]> response = controller.getHelpers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(testHelpers, response.getBody());
    }

    @Test
    void testGetHelpersIo() throws IOException {
        helper = mock(HelperDAO.class);
        when(helper.getHelpers()).thenThrow(new IOException("IOException"));
        HelperController helperController = new HelperController(helper);

        ResponseEntity<Helper[]> actualResponse = helperController.getHelpers();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
    }


    @Test
    void testCreateHelper() throws IOException {
        String username = "user";
        Helper expected = new Helper(username);
        when(helper.createHelper(username)).thenReturn(expected);
        ResponseEntity<Helper> response = controller.createHelper(username);
    
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    void testCreateHelperIo() throws IOException {
        String username = "test";
        helper = mock(HelperDAO.class);
        when(helper.createHelper(username)).thenThrow(new IOException("IOException"));
        HelperController helperController = new HelperController(helper);

        ResponseEntity<Helper> actualResponse = helperController.createHelper(username);
    
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
    }

    @Test
    void testSearchHelper() throws IOException {
        String username = "user1";
        when(helper.findHelpers(username)).thenReturn(testHelpers);

        ResponseEntity<Helper[]> response = controller.searchHelpers(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(testHelpers, response.getBody());
    }

    @Test
    void testSearchHelperIo() throws IOException {
        String username = "test";
        helper = mock(HelperDAO.class);
        when(helper.findHelpers(username)).thenThrow(new IOException("IOException"));
        controller = new HelperController(helper);

        ResponseEntity<Helper[]> actualResponse = controller.searchHelpers(username);
    
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
    }

    
    @Test
    void testUpdateHelper() throws IOException {
        String username = "test";
        Helper update = new Helper(username);
        when(helper.updateHelper(username, update)).thenReturn(update);

        ResponseEntity<Helper> response = controller.updateHelper(update);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(update, response.getBody());
    }

    @Test
    void testUpdateHelperIo() throws IOException {
        String username = "testUser";
        Helper update = new Helper(username);
        helper = mock(HelperDAO.class);
        when(helper.updateHelper(username, update)).thenThrow(new IOException("Simulating an IOException"));
        controller = new HelperController(helper);

        ResponseEntity<Helper> actualResponse = controller.updateHelper(update);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
    }

}
