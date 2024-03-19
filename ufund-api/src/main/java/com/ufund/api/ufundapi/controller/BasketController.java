package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ufund.api.ufundapi.model.Basket;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.NeedtoAdd;
import com.ufund.api.ufundapi.persistence.BasketDAO;

@RestController
@RequestMapping("baskets")
public class BasketController {
    
    private static final Logger LOG = Logger.getLogger(BasketController.class.getName());
    private BasketDAO basketDAO;

    /**
     * Construct a REST API controller for a {@link Basket}
     * 
     * @param basketDAO Data access object (Ex. FileDAO)
     */
    public BasketController(BasketDAO basketDAO) {
        this.basketDAO = basketDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain Basket basket} for the given id
     * 
     * @param id The id used to locate the {@link Basket basket}
     * 
     * @return ResponseEntity with {@link Basket basket} object and the HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Basket> getBasket(@PathVariable int id) {
        LOG.info("GET /baskets/" + id);
        try {
            Basket basket = basketDAO.getBasket(id);
            if (basket != null)
                return new ResponseEntity<Basket>(basket, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Basket basket} for a customer
     * 
     * @param basket - the {@link Basket basket} to create
     * @return ResponseEntity with created {@link Basket basket} object and HTTP
     *         status of CREATED
     *         ResponseEntity with HTTP status of CONFLICT if {@link Basket
     *         basket} object already exists
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Basket> createBasket(@RequestBody Basket basket) {
        LOG.info("POST /baskets " + basket);
        try {
            Basket result = basketDAO.createBasket(basket);

            if (result != null) {
                return new ResponseEntity<Basket>(result, HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Adds an item to a {@linkplain Basket basket} with the given id
     * 
     * @param id Id of the {@link Basket basket} to add the item to
     * @param need The {@link Need need} to add to the {@link Basket basket}
     * @return ResponseEntity with HTTP status of OK if added, NOT_FOUND if basket wasn't found
     */
    @PostMapping("addItem/{id}")
    public ResponseEntity<Basket> addItemToBasket(@PathVariable int id, @RequestBody NeedtoAdd needtoAdd) {
        LOG.info("POST /baskets/addItem/" + id + " " + needtoAdd);
        try {
            boolean successful = basketDAO.addItem(id, needtoAdd.getId(), needtoAdd.getQuantity());

            if (successful) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Removes an item from a {@linkplain Basket basket} with the given id
     * 
     * @param id Id of the {@link Basket basket} to remove the item from
     * @param needId Id of the {@link Need need} to remove from the {@link Basket basket}
     * @return ResponseEntity with HTTP status of OK if removed, NOT_FOUND if basket wasn't found
     */
    @PostMapping("removeItem/{id}")
    public ResponseEntity<Basket> removeItemFromBasket(@PathVariable int id, @RequestBody int needId) {
        LOG.info("POST /baskets/removeItem/" + id + " " + needId);
        try {
            boolean successful = basketDAO.removeItem(id, needId);

            if (successful) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("editItem/{id}")
    public ResponseEntity<Basket> editItemInBasket(@PathVariable int id, @RequestBody NeedtoAdd needtoAdd) {
        LOG.info("POST /baskets/editItem/" + id + " " + needtoAdd);
        try {
            boolean successful = basketDAO.editQuantity(id, needtoAdd.getId(), needtoAdd.getQuantity());

            if (successful) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Basket basket} with the provided {@linkplain Basket basket} object, if it exists
     *
     * @param basket The {@link Basket basket} to update
     *
     * @return ResponseEntity with updated {@link Basket basket} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Basket> updateBasket(@RequestBody Basket basket) {
        LOG.info("PUT /baskets " + basket);
        try {
            if(basketDAO.updateBasket(basket) != null)
                return new ResponseEntity<Basket>(basket, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Basket basket} with the given id
     * 
     * @param id - the id of the {@link Basket basket} to delete
     * @return ResponseEntity HTTP status of OK if deleted
     *         ResponseEntity with HTTP status of NOT_FOUND if not found
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Basket> deleteBasket(@PathVariable int id) {
        LOG.info("Delete /baskets/" + id);
        try {
            Basket basket = basketDAO.getBasket(id);
            if (basket != null) {
                basketDAO.deleteBasket(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}