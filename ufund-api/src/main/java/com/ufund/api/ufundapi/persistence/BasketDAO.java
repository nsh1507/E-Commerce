package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import com.ufund.api.ufundapi.model.Basket;

public interface BasketDAO {
    
    /**
     * Retrieves all {@link Basket basket} from the data store
     * 
     * @return Array of {@link Basket basket} objects, array may be empty, but will not be null
     * @throws IOException If the data store cannot be accessed
     */
    Basket[] getBaskets() throws IOException;

    /**
     * Retrieves a {@link Basket basket} from the file
     * 
     * @param id ID of the {@link Basket basket} corresponding to the user
     * @return {@link Basket basket} or null if not found
     * @throws IOException If the data store cannot be accessed
     */
    Basket getBasket(int id) throws IOException;

    /**
     * Updates a {@link Basket basket} in the data store
     * 
     * @param basket {@link Basket basket} to update
     * @return {@link Basket basket} (null if not found)
     * @throws IOException If the data store cannot be accessed
     */
    Basket updateBasket(Basket basket) throws IOException;

    /**
     * Adds an item to the basket
     * 
     * @param id Id of the basket
     * @param productId Id of the product
     * @param quantity Quantity of the product
     * @throws IOException If the data store cannot be accessed
     * @return True if the item was added, false if not
     */
    boolean addItem(int id, int productId, int quantity) throws IOException;

    /**
     * Removes an item from the basket
     * 
     * @param id Id of the basket
     * @param productId Id of the product
     * @throws IOException If the data store cannot be accessed
     * @return True if the item was removed, false if it was not found
     */
    boolean removeItem(int id, int productId) throws IOException;

    /**
     * Edits the quantity of an item in the basket
     * 
     * @param id Id of the basket
     * @param productId Id of the product
     * @param quantity Quantity of the product
     * @throws IOException If the data store cannot be accessed
     * @return True if the item was edited, false if it was not found
     */
    boolean editQuantity(int id, int productId, int quantity) throws IOException;

    /**
     * Saves a {@link Basket basket} to the data store
     * 
     * @param basket Created {@link Basket basket} to save
     * @return {@link Basket basket} (null if not found)
     * @throws IOException If the data store cannot be accessed
     */
    Basket createBasket(Basket basket) throws IOException;

    /**
     * Deletes a {@link Basket basket} from the data store
     * 
     * @param id ID of the {@link Basket basket} to delete
     * @return True if the ID {@link Basket} was deleted false if not
     * @throws IOException If the data store cannot be accessed
     */
    boolean deleteBasket(int id) throws IOException;
}