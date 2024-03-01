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
 * Handles the REST API requests for the Needs resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Nam Huynh
 */

@RestController
@RequestMapping("helpers")
public class HelperController {
    private static final Logger LOG = Logger.getLogger(HelperController.class.getName());
    private HelperDAO helperDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param helperDAO The {@link Need Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public HelperController(HelperDAO helperDAO) {
        this.helperDAO = helperDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain Helper helper} for the given id
     * 
     * @param id The id used to locate the {@link Helper helper}
     * 
     * @return ResponseEntity with {@link Helper helper} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Helper> getHelper(@PathVariable int id) {
        LOG.info("GET /helpers/" + id);
        try {
            Helper helper = helperDAO.getHelper(id);
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
     * Responds to the GET request for all {@linkplain Helper helper}
     * 
     * @return ResponseEntity with array of {@link Helper helper} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Helper[]> getHelpers() {
        LOG.info("GET /helpers");
        try {
            Helper[] helpersArray = helperDAO.getHelpers();
            if (helpersArray != null)
                return new ResponseEntity<Helper[]>(helpersArray,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    /**
     * Responds to the GET request for all {@linkplain Helper helper} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Helper helper}
     * 
     * @return ResponseEntity with array of {@link Helper helper} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
    */

    @GetMapping("/")
    public ResponseEntity<Helper[]> searchHelpers(@RequestParam String name) {
        LOG.info("GET /helpers/?name="+name);
        try {
            Helper[] helpersArray = helperDAO.findHelpers(name);
            return new ResponseEntity<Helper[]>(helpersArray,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Helper helper} with the provided helper object
     * 
     * @param helper - The {@link Helper helper} to create
     * 
     * @return ResponseEntity with created {@link Helper helper} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Helper helper} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Helper> createNeed(@RequestBody Helper helper) {
        LOG.info("POST /helpers " + helper);

        try {
            Helper[] conflicts = helperDAO.findHelpers(helper.getName());
            for (Helper n : conflicts) {
                if (n.getName().equals(helper.getName())) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }
            Helper helperResponse = helperDAO.createHelper(helper);
            return new ResponseEntity<Helper>(helperResponse, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Helper helper} with the provided {@linkplain Helper helper} object, if it exists
     * 
     * @param helpers The {@link Helper helper} to update
     * 
     * @return ResponseEntity with updated {@link Helper helper} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Helper> updateHelpers(@RequestBody Helper helpers) {
        LOG.info("PUT /helpers " + helpers);
        try {   
            Helper updated = helperDAO.updateHelpers(helpers);
            if (updated != null){
                return new ResponseEntity<Helper>(updated, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Need need} with the given id
     * 
     * @param id The id of the {@link Need need} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Helper> deleteHelper(@PathVariable int id) {
        LOG.info("DELETE /helpers/" + id);
        try {
           Boolean deleted = helperDAO.deleteHelper(id);
           if (deleted){
               return new ResponseEntity<>(HttpStatus.OK);
           }
           else{
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
