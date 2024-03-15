package com.ufund.api.ufundapi.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.Basket;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.BasketDAO;
import com.ufund.api.ufundapi.persistence.NeedDAO;

@RestController
@RequestMapping("basket")
public class BasketController {
    
    private BasketDAO basketDAO;
    private NeedDAO needDAO;

    /**
     * Creates a REST API controller to reponds to basket-related requests
     * 
     * @param basketDAO The {@link Basket Data Access Object} to perform CRUD operations
     * @param needDAO The {@link Need Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public BasketController( BasketDAO basketDAO, NeedDAO needDAO) {
        this.basketDAO = basketDAO;
        this.needDAO = needDAO;
    }

    /**
     * Adds a {@link Need need} to a Helper's funding basket.
     * Checks if the product is in the needs cupboard and if it's
     * already in the funding basket.
     * @param need the need to be added.
     * @param id the id of the basket being added to (matches the Helper's id)
     * @return {@link ResponseEntity ResponseEntity} HTTP status CONFLICT if the product is already in basket
     *         {@link ResponseEntity ResponseEntity} HTTP status CREATED if successful
     *         {@link ResponseEntity ResponseEntity} HTTP status NOT_FOUND if need doesn't exist
     *         {@link ResponseEntity ResponseEntity} HTTP status INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("/baskets/{id}/need")
    public ResponseEntity<Need> addToBasket( @RequestBody Need need, @PathVariable int id) {
        try {
            // locate product in cupboard
            basketDAO.updateBasket(id);
            Need[] results = needDAO.findNeeds( need.getName() );
            boolean located = false;
            for( Need current : results ) {
                located = (current.getName().equals(need.getName()));
                if( located ) break;
            }
            if( ! located ) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            // Check if need is already in basket
            results = basketDAO.searchBasket( id, need.getName());
            for( Need current : results) {
                if ( current.getName().equals(need.getName()) ) {
                    return new ResponseEntity<Need>(HttpStatus.CONFLICT);
                }
            }
            // Add to the basket
            basketDAO.addToBasket(id, need);
            return new ResponseEntity<Need>(need, HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets the contents of a Helper's funding basket.
     * @param id: the id of the funding basket.
     * @return {@link ResponseEntity ResponseEntity} HTTP status OK if successful
     *         {@link ResponseEntity ResponseEntity} HTTP status of NOT_FOUND if basket doesn't exist
     *         {@link ResponseEntity ResponseEntity} HTTP status INTERNAL_SERVER_ERROR otherewise
     */
    @GetMapping("/baskets/{userid}")
    public ResponseEntity<Need[]> getBasketContents( @PathVariable int id ) throws IOException {
        try {
            basketDAO.updateBasket(id);

            Need[] contents = basketDAO.getContents(id); 
            return new ResponseEntity<Need[]>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<Need[]>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    /**
     * Get a need from the Basket using its id.
     * @param id the id of the need.
     * @return {@link ResponseEntity ResponseEntity} {@link Need need} object and HTTP status FOUND if found
     *         {@link ResponseEntity ResponseEntity} HTTP status NOT_FOUND if not in basket
     *         {@link ResponseEntity ResponseEntity} HTTP status INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/baskets/{id}/need/{needid}")
    public ResponseEntity<Need> getNeed( @PathVariable int id, @PathVariable int needid) {
        try {
            basketDAO.updateBasket(id);
            Need need = basketDAO.getBasketNeed(id, needid);
            if( need == null ) {
                return new ResponseEntity<Need>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Need>(need, HttpStatus.OK);
            }
        } catch( IOException e ) {
           return new ResponseEntity<Need>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    /**
     * 
     * @param id the id of the basket.
     * @param needid the id of the need being deleted.
     * @return {@link ResponseEntity ResponseEntity} HTTP status OK if successful
     *         {@link ResponseEntity ResponseEntity} HTTP status NOT_FOUND if need not found
     *         {@link ResponseEntity ResponseEntity} HTTP status INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/baskets/{id}/need/{needid}")
    public ResponseEntity<Need> deleteNeed( @PathVariable int id, @PathVariable int needid) {
        try {
            basketDAO.updateBasket(id);

            Need need = basketDAO.getBasketNeed(id, needid);
            if( need == null ) {
                return new ResponseEntity<Need>(HttpStatus.NOT_FOUND);
            } else {
                basketDAO.removeFromBasket(id, needid);
                return new ResponseEntity<Need>(HttpStatus.OK);
            }
        } catch( IOException e) {
            return new ResponseEntity<Need>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Removes everything in the funding basket
     * @param id the id of the basket
     * @return {@link ResponseEntity ResponseEntity} HTTP status OK if successful
     *         {@link ResponseEntity ResponseEntity} HTTP status INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/baskets/{id}/")
    public ResponseEntity<Need> clearBasket( @PathVariable int id ) {
        try {
            basketDAO.updateBasket(id);
            basketDAO.clearBasket(id);
            return new ResponseEntity<Need>(HttpStatus.OK);
        } catch( IOException e) {
            return new ResponseEntity<Need>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 
     * @param id the id of the basket
     * @param name the keyword being searched
     * @return {@link ResponseEntity ResponseEntity} HTTP status OK on success
     *         {@link ResponseEntity ResponseEntity} HTTP status NOT_FOUND if product can't be located
     *         {@link ResponseEntity ResponseEntity} HTTP status INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/baskets/{id}/")
    public ResponseEntity<Need[]> searchBasket(@PathVariable int id, @RequestParam String name) {
        try {
            basketDAO.updateBasket(id);

            Need[] needs = basketDAO.searchBasket(id, name);
            if( needs.length != 0 ) {
                return new ResponseEntity<Need[]>(needs, HttpStatus.OK);
            } else {
                return new ResponseEntity<Need[]>(HttpStatus.NOT_FOUND);
            }
        } catch( IOException e ) {
            return new ResponseEntity<Need[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}