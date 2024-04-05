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

import com.ufund.api.ufundapi.persistence.FeedbackDAO;
import com.ufund.api.ufundapi.model.Feedback;

/**
 * Handles the REST API requests for the Feedbacks resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Nam Huynh
 */

@RestController
@RequestMapping("feedbacks")
public class FeedbackController {
    private static final Logger LOG = Logger.getLogger(FeedbackController.class.getName());
    private FeedbackDAO feedbackDAO;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param feedbackDAO The {@link Feedback Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public FeedbackController(FeedbackDAO feedbackDAO) {
        this.feedbackDAO = feedbackDAO;
    }



    /**
     * Responds to the GET request for all {@linkplain Feedbacks feedbacks}
     * 
     * @return ResponseEntity with array of {@link Feedbacks feedbacks} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Feedback[]> getFeedbacks() {
        LOG.info("GET /feedbacks");
        try {
            Feedback[] feedbacksArray = feedbackDAO.getFeedbacks();
            if (feedbacksArray != null)
                return new ResponseEntity<Feedback[]>(feedbacksArray,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    /**
     * Creates a {@linkplain Feedback feedback} with the provided feedbacks object
     * 
     * @param feedback - The {@link Feedback feedback} to create
     * 
     * @return ResponseEntity with created {@link Feedback feedback} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Feedback feedback} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
        LOG.info("POST /feedbacks " + feedback);
        try {
            Feedback newFeedback = feedbackDAO.createFeedback(feedback);
            return new ResponseEntity<Feedback>(newFeedback,HttpStatus.CREATED);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Feedback feedback} with the provided {@linkplain Feedbacks feedbacks} object, if it exists
     * 
     * @param feedbacks The {@link Feedback feedback} to update
     * 
     * @return ResponseEntity with updated {@link Feedback feedback} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Feedback> updateFeedbacks(@RequestBody Feedback feedbacks) {
        LOG.info("PUT /feedbacks " + feedbacks);
        try {   
            Feedback updated = feedbackDAO.updateFeedback(feedbacks);
            if (updated != null){
                return new ResponseEntity<Feedback>(updated, HttpStatus.OK);
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
     * Deletes a {@linkplain Feedback feedback} with the given id
     * 
     * @param id The id of the {@link Feedback feedback} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Feedback> deleteFeedback(@PathVariable int id) {
        LOG.info("DELETE /feedbacks/" + id);
        try {
           Boolean deleted = feedbackDAO.deleteFeedback(id);
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
