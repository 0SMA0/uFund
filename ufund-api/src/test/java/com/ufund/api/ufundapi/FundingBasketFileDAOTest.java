package com.ufund.api.ufundapi;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.FundingBasket;

import com.ufund.api.ufundapi.persistence.FundingBasketFileDAO;

@Tag("Persistence Tier")
public class FundingBasketFileDAOTest {
    FundingBasketFileDAO basketDAO;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupFundingBasketDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        FundingBasket[] mockBasketArray = new FundingBasket[]{
            new FundingBasket("mockBasket1"), 
            new FundingBasket("mockBasket2")};
        when(mockObjectMapper.readValue(new File("doesnt_matter.txt"), FundingBasket[].class)).thenReturn(mockBasketArray);

        basketDAO = new FundingBasketFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testGetBaskets() throws IOException {
        FundingBasket[] actualBaskets = basketDAO.getBaskets();
        assertEquals("mockBasket1", actualBaskets[0].getUsername());
        assertEquals("mockBasket2", actualBaskets[1].getUsername());
    }

    @Test
    public void testAddNeed() throws IOException { 
        FundingBasket testBasket = basketDAO.getBasket("mockBasket1");
        Need testNeed = new Need("name", 1, 1, "type");
        when(mockObjectMapper.readValue(new File("doesnt_matter.txt"), FundingBasket[].class)).thenReturn(new FundingBasket[]{testBasket});
        basketDAO.addNeed(testBasket, testNeed);
        assertNotNull(testBasket);
        assertEquals(testNeed, testBasket.getBasket().get("name"));
    }

    @Test
    public void testRemoveNeed() throws IOException {
        FundingBasket testBasket = basketDAO.getBasket("mockBasket1");
        Need testNeed = new Need("name", 1, 1, "type");
        when(mockObjectMapper.readValue(new File("doesnt_matter.txt"), FundingBasket[].class)).thenReturn(new FundingBasket[]{testBasket});
        basketDAO.addNeed(testBasket, testNeed);
        basketDAO.removeNeed(testBasket, testNeed);
        assertEquals(0, testBasket.getBasket().size());
    }

    @Test
    public void testCheckOut() throws IOException {
        FundingBasket testBasket = basketDAO.getBasket("mockBasket1");
        Need testNeed = new Need("name", 1, 1, "type");
        when(mockObjectMapper.readValue(new File("doesnt_matter.txt"), FundingBasket[].class)).thenReturn(new FundingBasket[]{testBasket});
        basketDAO.addNeed(testBasket, testNeed);
        basketDAO.checkOut(testBasket);
        assertEquals(0, testBasket.getBasket().size());
    }

    @Test
    public void testCreateBasket() throws IOException {
        FundingBasket newBasket = basketDAO.createBasket("Christian");
        assertNotNull(newBasket);
        assertEquals("Christian", newBasket.getUsername());
    }

}
