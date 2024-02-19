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

import com.ufund.api.ufundapi.persistence.NeedsDAO;
import com.ufund.api.ufundapi.model.Needs;

/**
 * Handles the REST API requests for the Needs resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Nam Huynh
 */

@RestController
@RequestMapping("needs")
public class NeedsController {
    private static final Logger LOG = Logger.getLogger(NeedsController.class.getName());
    private NeedsDAO needDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param needDAO The {@link Needs Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public NeedsController(NeedsDAO needDAO) {
        this.needDAO = needDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain Needs needs} for the given id
     * 
     * @param id The id used to locate the {@link Needs needs}
     * 
     * @return ResponseEntity with {@link Needs needs} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Needs> getNeed(@PathVariable int id) {
        LOG.info("GET /needs/" + id);
        try {
            Needs need = needDAO.getNeed(id);
            if (need != null)
                return new ResponseEntity<Needs>(need,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Needs needs}
     * 
     * @return ResponseEntity with array of {@link Needs needs} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Needs[]> getNeeds() {
        LOG.info("GET /needs");
        try {
            Needs[] needsArray = needDAO.getNeeds();
            if (needsArray != null)
                return new ResponseEntity<Needs[]>(needsArray,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    /**
     * Responds to the GET request for all {@linkplain Needs needs} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Needs needs}
     * 
     * @return ResponseEntity with array of {@link Needs needs} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
    */

    @GetMapping("/")
    public ResponseEntity<Needs[]> searchNeeds(@RequestParam String name) {
        LOG.info("GET /needs/?name="+name);
        try {
            Needs[] needsArray = needDAO.findNeeds(name);
            return new ResponseEntity<Needs[]>(needsArray,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Needs needs} with the provided needs object
     * 
     * @param need - The {@link Needs needs} to create
     * 
     * @return ResponseEntity with created {@link Needs needs} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Needs needs} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Needs> createNeed(@RequestBody Needs need) {
        LOG.info("POST /needs " + need);
        try {   
            Needs new_Need = needDAO.createNeed(need);
            if (new_Need != null){
                return new ResponseEntity<Needs>(new_Need,HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Needs needs} with the provided {@linkplain Needs needs} object, if it exists
     * 
     * @param needs The {@link Needs needs} to update
     * 
     * @return ResponseEntity with updated {@link Needs needs} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Needs> updateNeeds(@RequestBody Needs needs) {
        LOG.info("PUT /heroes " + needs);
        try {   
            Needs updated = needDAO.updateNeed(needs);
            if (updated != null){
                return new ResponseEntity<Needs>(updated, HttpStatus.OK);
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
     * Deletes a {@linkplain Needs needs} with the given id
     * 
     * @param id The id of the {@link Needs needs} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Needs> deleteNeed(@PathVariable int id) {
        LOG.info("DELETE /heroes/" + id);
        try {
           Boolean deleted = needDAO.deleteNeed(id);
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
