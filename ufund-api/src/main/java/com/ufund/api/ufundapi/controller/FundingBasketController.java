package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.FundingBasket;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.FundingBasketDAO;

@RestController
@RequestMapping("baskets")
public class FundingBasketController {
    private static final Logger LOG = Logger.getLogger(FundingBasketController.class.getName());
    // private CupboardDAO cupboard;
    private FundingBasketDAO basketDAO;

    public FundingBasketController(FundingBasketDAO basketDAO) {
        // this.cupboard = cupboard;
        this.basketDAO = basketDAO;
    }

    @GetMapping("{username}")
    public ResponseEntity<FundingBasket> getBasket(@PathVariable String username) throws IOException{
        try {
            LOG.info("GET /baskets/" + username);
            LOG.info(basketDAO.getBasket(username).toString());
            FundingBasket basket = basketDAO.getBasket(username);
            if (username != null)
                
                return new ResponseEntity<FundingBasket>(basket, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<FundingBasket[]> getBaskets() {
        LOG.info("GET /baskets");
        try{
            FundingBasket[] baskets = basketDAO.getBaskets();
            if (baskets != null)
                return new ResponseEntity<FundingBasket[]>(baskets, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<FundingBasket> createBasket(@RequestBody String username) {
        LOG.info("POST /baskets/" + username);
        try {
            return new ResponseEntity<FundingBasket>(basketDAO.createBasket(username),HttpStatus.OK);
        } 
        catch (IOException e) {
            System.out.println("w9ufgwoefh");

            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("add/{username}")
    public ResponseEntity<FundingBasket> addNeed(@PathVariable String username, @RequestBody Need need) throws IOException {
        LOG.info("PUT /baskets/add " + username);
        LOG.info(basketDAO.getBasket(username).toString());
        LOG.info(need.getName());
        try {
            FundingBasket basket = basketDAO.getBasket(username);
            basketDAO.addNeed(basket, need);
            return new ResponseEntity<FundingBasket>(basket,HttpStatus.OK);
        } 
        catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("remove/{username}")
    public ResponseEntity<FundingBasket> removeNeed(@PathVariable String username, @RequestBody Need need) {
        LOG.info("PUT /baskets/remove " + username);
        try {
            FundingBasket basket = basketDAO.getBasket(username);
            basketDAO.removeNeed(basket, need);
            return new ResponseEntity<FundingBasket>(basket,HttpStatus.OK);
        } 
        catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("checkout")
    public ResponseEntity<FundingBasket> checkOut(@RequestBody String username) {
        LOG.info("PUT /baskets/checkout " + username);
        try {
            FundingBasket basket = basketDAO.getBasket(username);
            basketDAO.checkOut(basket);
            return new ResponseEntity<FundingBasket>(basket,HttpStatus.OK);
        } 
        catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
