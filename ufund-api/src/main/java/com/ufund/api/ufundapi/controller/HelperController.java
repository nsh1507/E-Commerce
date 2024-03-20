package com.ufund.api.ufundapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ufund.api.ufundapi.persistence.HelperDAO;
import com.ufund.api.ufundapi.model.Helper;

/**
 * Handles the REST API requests for the Helper resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 */

@RestController
@RequestMapping("helpers")
public class HelperController {
    private static final Logger LOG = Logger.getLogger(HelperController.class.getName());
    private HelperDAO helperDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param helperDao The {@link HelperDAO Helper Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public HelperController(HelperDAO helperDao) {
        this.helperDao = helperDao;
    }

    /**
     * Responds to the GET request for an {@linkplain Helper helper} for the given username
     * 
     * @param username The username used to locate the {@link Helper helper}
     * 
     * @return ResponseEntity with {@link Helper helper} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{username}")
    public ResponseEntity<Helper> getHelper(@PathVariable String username) {
        LOG.info("GET /helpers/" + username);
        try {
            Helper helper = helperDao.getHelper(username);
            if (helper != null)
                return new ResponseEntity<Helper>(helper,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Helper helpers}
     * 
     * @return ResponseEntity with array of {@link Helper helper} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Helper[]> getHelpers() {
        LOG.info("GET /helpers");
        try {
            Helper[] helperArray = helperDao.getHelpers();
            if (helperArray != null)
                return new ResponseEntity<Helper[]>(helperArray,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for an {@linkplain Helper helper} for the given username and password
     * 
     * @param username The username used to login to the {@link Helper helper}
     * @param password The username used to login to the {@link Helper helper}
     * 
     * @return ResponseEntity with {@link Helper helper} object and HTTP status of OK if logged in<br>
     * ResponseEntity with HTTP status of UNAUTHORIZED if not logged in<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping(path = "/{username}", params = "password")
    public ResponseEntity<Helper> loginHelper(@PathVariable String username, @RequestParam String password) {
        LOG.info("GET /helpers/" + username + "?password="+ password);
        try {
            Helper helper = helperDao.loginHelper(username, password);
            if (helper != null)
                return new ResponseEntity<Helper>(helper,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates an {@linkplain Helper helper} with the provided helper object
     * 
     * @param helper - The {@link Helper helper} to create
     * 
     * @return ResponseEntity with created {@link Helper helper} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Helper helper} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Helper> createHelper(@RequestBody Helper helper) {
        LOG.info("POST /helpers " + helper);

        try {
            if (helperDao.getHelper(helper.getUsername()) != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            
            Helper newHelper = helperDao.createHelper(helper);
            return new ResponseEntity<Helper>(newHelper,HttpStatus.CREATED);
                
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Updates the {@linkplain Helper helper} with the provided {@linkplain Helper helper} object, if it exists
     * 
     * @param helper The {@link Helper helper} to update
     * 
     * @return ResponseEntity with updated {@link Helper helper} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Helper> updateHelper(@RequestBody Helper helper) {
        LOG.info("PUT /helpers " + helper);

        try {
            Helper newHelper = helperDao.updateHelper(helper);
            if (newHelper != null)
                return new ResponseEntity<Helper>(newHelper,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes an {@linkplain Helper helper} with the given username
     * 
     * @param username The username of the {@link Helper helper} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<Helper> deleteHelper(@PathVariable String username) {
        LOG.info("DELETE /helpers/" + username);

        try {
            if ( helperDao.deleteHelper(username) )
                return new ResponseEntity<Helper>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Responds to the GET request for checking out the current user's basket
     * 
     * @return ResponseEntity with an HTTP status of OK and body of true if the basket was checked
     *         out<br>
     *       
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/checkout")
    public ResponseEntity<Boolean> checkoutBasket() {
        LOG.info("GET /checkout");
        try {
            return new ResponseEntity<>(helperDao.checkoutBasket(), HttpStatus.OK);
        
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}