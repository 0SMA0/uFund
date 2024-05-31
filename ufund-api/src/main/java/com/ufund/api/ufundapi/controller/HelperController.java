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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.Helper;
import com.ufund.api.ufundapi.persistence.HelperDAO;

@RestController
@RequestMapping("helpers")
public class HelperController {
    private static final Logger LOG = Logger.getLogger(HelperController.class.getName());
    // private CupboardDAO cupboard;
    private HelperDAO helperDAO;


    public HelperController(HelperDAO helperDAO) {
        // this.cupboard = cupboard;
        this.helperDAO = helperDAO;
    }

    // private Need[] getNeedsArray(String containsText, Map<String, Need> funding_basket) {
    //     List<Need> needList = new ArrayList<Need>();
    //     for(String key : funding_basket.keySet()){
    //         if(containsText == null || key.contains(containsText)){
    //             Need need = funding_basket.get(key);
    //             needList.add(need);
    //         }
    //     }
    //     Need[] needArray = new Need[needList.size()];
    //     needList.toArray(needArray);
    //     return needArray;
    // }

    @GetMapping("/{username}")
    public ResponseEntity<Helper> getHelper(@PathVariable String username) {
        LOG.info("GET /helpers/" + username);
        try {
            Helper helper = helperDAO.getHelper(username);
            if (helper != null)
                return new ResponseEntity<Helper>(helper, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // @GetMapping("/{username}/{funding_basket}")
    // public ResponseEntity<Need[]> getFundingBasket(@PathVariable String username) {
    //     LOG.info("GET /helpers/funding_basket/" + username);
    //     try {
    //         Helper helper = helperDAO.getHelper(username);
    //         if (username != null){
    //             Map<String, Need> funding_basket = helper.getFundingBasket();
    //             Need[] needs = getNeedsArray(username, funding_basket);
    //             return new ResponseEntity<Need[]>(needs, HttpStatus.OK);
    //         }
    //         else
    //             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    //     catch(IOException e) {
    //         LOG.log(Level.SEVERE,e.getLocalizedMessage());
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }


    @GetMapping("")
    public ResponseEntity<Helper[]> getHelpers() {
        LOG.info("GET /helpers");
        try{
            Helper[] helpers = helperDAO.getHelpers();
            if (helpers != null)
                return new ResponseEntity<Helper[]>(helpers, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Helper> createHelper(@RequestBody String username) {
        LOG.info("POST /helpers " + username);
        try {
            return new ResponseEntity<Helper>(helperDAO.createHelper(username),HttpStatus.OK);
        } 
        catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Helper[]> searchHelpers(@RequestParam String username) throws IOException{
        LOG.info("GET /helpers/?username="+username);
        try{
            Helper[] helpers = helperDAO.findHelpers(username);
            if (helpers != null && helpers.length !=0) 
                return new ResponseEntity<Helper[]>(helpers, HttpStatus.OK);
            else 
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Helper> updateHelper(@RequestBody Helper helper) {
        LOG.info("PUT /helpers " + helper);
        try {
            return new ResponseEntity<Helper>(helperDAO.updateHelper(helper.getUsername(), helper), HttpStatus.OK);
        } 
        catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
