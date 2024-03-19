package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

import com.ufund.api.ufundapi.model.Basket;
import com.ufund.api.ufundapi.persistence.BasketDAO;

@RestController
@RequestMapping("baskets")
public class BasketController {
    
    private static final Logger LOG = Logger.getLogger(BasketController.class.getName());
    private BasketDAO basketDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param basketDao The {@link basketDao Basket Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public BasketController(BasketDAO basketDao) {
        this.basketDao = basketDao;
    }
    

    /**
     * Responds to the GET request for a {@linkplain Basket basket} for the given id
     * 
     * @param id The id used to locate the {@link Basket basket}
     * 
     * @return ResponseEntity with {@link Basket basket} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Basket> getBasket(@PathVariable int id) {
        LOG.info("GET /basketes/" + id);
        try {
            Basket basket = basketDao.getBasket(id);
            if (basket != null)
                return new ResponseEntity<Basket>(basket,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Responds to the GET request for multiple {@linkplain Basket basket}
     * 
     * 
     * @return ResponseEntity with {@link Basket basket} objects and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Basket[]> getBasketes() {
        LOG.info("GET /basketes/");
        try {
            Basket[] basketes = basketDao.getBasketes();
            if (basketes[0] != null)
                return new ResponseEntity<Basket[]>(basketes, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Responds to the GET request for all {@linkplain Basket basketes} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Basket Basketes}
     * 
     * @return ResponseEntity with array of {@link Basket Basket} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all Basketes that contain the text "ma"
     * GET http://localhost:8080/Basketes/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Basket[]> searchBasketes(@RequestParam String name) {
        LOG.info("GET /Basketes/?name="+name);
        try {
            Basket[] Basketlist = basketDao.getBasketes();
            Basket[] Basketes = new Basket[basketDao.getBasketes().length];
            int k = 0;
            for (int i = 0; i < Basketes.length; i++) {
                if(Basketlist[i].getName().contains(name)){
                    Basketes[k] = Basketlist[i];
                    k++;
                }
            }
            Basket[] Basketes2 = new Basket[k];
            for (int i = 0; i < Basketes2.length; i++) {
                Basketes2[i] = Basketes[i];
            }


            if (Basketes2.length != 0)
                return new ResponseEntity<Basket[]>(Basketes2,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Replace below with your implementation
    }

    /**
     * Creates a {@linkplain Basket Basket} with the provided Basket object
     * 
     * @param Basket - The {@link Basket Basket} to create
     * 
     * @return ResponseEntity with created {@link Basket Basket} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Basket Basket} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Basket> createBasket(@RequestBody Basket basket) {
        LOG.info("POST /Basketes " + basket);
        try {
            //ArrayList<Product> prods = new ArrayList<>();
            Basket fileBasket = basketDao.createBasket(basket);
            return new ResponseEntity<Basket>(fileBasket,HttpStatus.CREATED);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Basket Basket} with the provided {@linkplain Basket Basket} object, if it exists
     * 
     * @param Basket The {@link Basket Basket} to update
     * 
     * @return ResponseEntity with updated {@link Basket Basket} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Basket> updateBasket(@RequestBody Basket Basket) {
        LOG.info("PUT /Basketes " + Basket);
        try {
            Basket newBasket = basketDao.updateBasket(Basket);
        if (newBasket != null)
            return new ResponseEntity<Basket>(newBasket,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Basket Basket} with the given id
     * 
     * @param id The id of the {@link Basket Basket} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Basket> deleteBasket(@PathVariable int id) {
        LOG.info("DELETE /Basketes/" + id);
        try {
            boolean tf = basketDao.deleteBasket(id);
        if (tf == true)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}