package com.ufund.api.ufundapi;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ufund.api.ufundapi.controller.FundingBasketController;
import com.ufund.api.ufundapi.model.FundingBasket;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.FundingBasketDAO;


public class FundingBasketControllerTest {
    private FundingBasketDAO basketDAO;

    @BeforeEach
    public void setupFundingBasketController() {
        basketDAO = mock(FundingBasketDAO.class);
    } 

    @Test
    void testGetBasket() throws IOException {
        FundingBasket expectedBasket = new FundingBasket("Christian");
        FundingBasketDAO mockDAO = mock(FundingBasketDAO.class);
        when(mockDAO.getBasket("Christian")).thenReturn(expectedBasket);

        FundingBasketController controller = new FundingBasketController(mockDAO);
        ResponseEntity<FundingBasket> response = controller.getBasket("Christian");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBasket, response.getBody());
    }
    // @Test
    // void testGetBasketNotFound() throws IOException {
    //     String username = "test";
    //     basketDAO = mock(FundingBasketDAO.class);
    //     when(basketDAO.getBasket(username)).thenReturn(null);
    //     FundingBasketController basket = new FundingBasketController(basketDAO);

    //     ResponseEntity<FundingBasket> actualResponse = basket.getBasket(username);
    //     assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    // }

    @Test
    void testGetBasketIo() throws IOException {
        String username = "test";
        basketDAO = mock(FundingBasketDAO.class);
        when(basketDAO.getBasket(username)).thenThrow(new IOException("IOException"));
        FundingBasketController basket = new FundingBasketController(basketDAO);

        ResponseEntity<FundingBasket> actualResponse = basket.getBasket(username);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
    }

    @Test
    void testGetBaskets() throws IOException {
        FundingBasket[] expectedBaskets = {new FundingBasket("Christian")};
        FundingBasketDAO mockDAO = mock(FundingBasketDAO.class);
        when(mockDAO.getBaskets()).thenReturn(expectedBaskets);

        FundingBasketController controller = new FundingBasketController(mockDAO);

        ResponseEntity<FundingBasket[]> responseEntity = controller.getBaskets();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertArrayEquals(expectedBaskets, responseEntity.getBody());
    }

    @Test
    void testCreateBasket() throws IOException {
        FundingBasket expectedBasket = new FundingBasket("Christian");
        FundingBasketDAO mockDAO = mock(FundingBasketDAO.class);
        when(mockDAO.createBasket("Christian")).thenReturn(expectedBasket);

        FundingBasketController controller = new FundingBasketController(mockDAO);
        ResponseEntity<FundingBasket> response = controller.createBasket("Christian");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBasket, response.getBody());
    }

    @Test
    void testAddNeed() throws IOException {
        String username = "Sam";
        Need newNeed = new Need("name", 1, 1, "type");

        FundingBasket existingBasket = new FundingBasket("Christian");
        FundingBasketDAO mockDAO = mock(FundingBasketDAO.class);
        when(mockDAO.getBasket(username)).thenReturn(existingBasket);

        FundingBasketController controller = new FundingBasketController(mockDAO);

        ResponseEntity<FundingBasket> responseEntity = controller.addNeed(username, newNeed);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(existingBasket, responseEntity.getBody());
    }

    @Test
    void testRemoveNeed() throws IOException {
        String username = "Sam";
        Need needToRemove = new Need("name", 1, 1, "type");

        FundingBasket existingBasket = new FundingBasket("Christian");
        FundingBasketDAO mockDAO = mock(FundingBasketDAO.class);
        when(mockDAO.getBasket(username)).thenReturn(existingBasket);

        FundingBasketController controller = new FundingBasketController(mockDAO);

        ResponseEntity<FundingBasket> responseEntity = controller.removeNeed(username, needToRemove);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(existingBasket, responseEntity.getBody());
    }

    @Test
    void testCheckout() throws IOException {
        String username = "Sam";

        FundingBasket existingBasket = new FundingBasket("Christian");
        FundingBasketDAO mockDAO = mock(FundingBasketDAO.class);
        when(mockDAO.getBasket(username)).thenReturn(existingBasket);

        FundingBasketController controller = new FundingBasketController(mockDAO);

        ResponseEntity<FundingBasket> responseEntity = controller.checkOut(username);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(existingBasket, responseEntity.getBody());
    }

}
