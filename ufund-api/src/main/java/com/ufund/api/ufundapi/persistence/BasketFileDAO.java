package com.ufund.api.ufundapi.persistence;

import java.util.Map;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.util.TreeMap;


import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.Basket;

@Component
public class BasketFileDAO implements BasketDAO {

    private static final Logger LOG = Logger.getLogger(BasketFileDAO.class.getName());

    /** JSON serializer/deserializer */
    private ObjectMapper objectMapper;

    /** Maps ids to their corresponding {@link Product} */
    Map<Integer, Basket> basketMap;

    /** Next id to assign */
    private static int nextId;

    private String filename;

    /**
     * Build the {@link BasketFileDAO}
     * 
     * @param filename Filename to store the data in
     * @param objectMapper JSON serializer/deserializer
     * 
     * @throws IOException If there is an error reading the file
     */
    public BasketFileDAO(@Value("${baskets.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.basketMap = new TreeMap<>();
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Gets next id and increments it
     * 
     * Syncronized to prevent multiple threads from accessing nextId concurrently
     * 
     * @return The next id to assign
     */
    private synchronized static int getNextId() {
        return nextId++;
    }

    /**
     * Gets array of {@link Basket baskets} from the file
     * 
     * @return Array of stored {@link Basket baskets}, or empty array if none
     */
    private Basket[] getBasketsArray() {
        Basket[] basketArray = new Basket[basketMap.size()];
        basketMap.values().toArray(basketArray);

        return basketArray;
    }

    /**
     * Saves {@link Basket} to the DAO file
     * 
     * @throws IOException If there is an error writing to the file
     */
    private void save() throws IOException{
        Basket[] allBaskets = getBasketsArray();

        // Uses object mapper to convert to json and write to file
        objectMapper.writeValue(new File(filename), allBaskets);
    }

    /**
     * Load all {@link Basket} from the DAO file and store in the map
     * 
     * @throws IOException If there is an error reading the file
     */
    private void load() throws IOException {
        basketMap.clear();
        nextId = 0;

        Basket[] serializedBaskets = objectMapper.readValue(new File(filename), Basket[].class);

        // Add all baskets and keep track of the greatest id
        for (Basket basket: serializedBaskets) {
            basketMap.put(basket.getId(), basket);
            if (basket.getId() > nextId) {
                nextId = basket.getId();
            }
        }

        // Incremement id again so it is ready to return the next id
        getNextId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Basket[] getBaskets() {
        synchronized(basketMap) {
            return getBasketsArray();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Basket getBasket(int id) {
        synchronized(basketMap) {
            return basketMap.get(id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Basket createBasket(Basket basket) throws IOException {
        synchronized(basketMap) {
            Basket tmpBasket = new Basket(getNextId());
            
            // Add to map and save to DAO
            basketMap.put(tmpBasket.getId(), tmpBasket);
            save();

            return tmpBasket;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteBasket(int id) throws IOException {
        synchronized (basketMap) {
            if (!basketMap.containsKey(id)) {
                return false;
            }
            else {
                basketMap.remove(id);
                save();
                return true;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Basket updateBasket(Basket basket) throws IOException {
        synchronized (basketMap) {
            if (!basketMap.containsKey(basket.getId())) {
                return null;
            }
            else {
                basketMap.put(basket.getId(), basket);
                save();
                return basket;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addItem(int basketId, int productId, int quantity) throws IOException {
        synchronized (basketMap) {
            Basket basket = basketMap.get(basketId);
            if (basket == null) {
                return false;
            }
            basket.addItem(productId, quantity);
            save();
            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeItem(int basketId, int productId) throws IOException {
        synchronized (basketMap) {
            Basket basket = basketMap.get(basketId);
            if (basket == null) {
                return false;
            }
            basket.removeItem(productId);
            save();
            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean editQuantity(int basketId, int productId, int quantity) throws IOException {
        synchronized (basketMap) {
            Basket basket = basketMap.get(basketId);
            if (basket == null) {
                return false;
            }
        
            basket.editQuantity(productId, quantity);
            save();
            return true;
        }
    }
    
}
