package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import com.ufund.api.ufundapi.model.*;

public interface BasketDAO {
    
   /**
     * Retrieves all {@linkplain Basket basketes}
     * 
     * @return An array of {@link Basket basket} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Basket[] getBasketes() throws IOException;

    /**
     * Finds all {@linkplain Basket basketes} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Basket basketes} whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Basket[] findBasketes(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Basket basket} with the given id
     * 
     * @param id The id of the {@link basket basket} to get
     * 
     * @return a {@link Basket basketes} basket with the matching id
     * <br>
     * null if no {@link Basket basket} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Basket getBasket(int id) throws IOException;

  

    /**
     * Creates and saves a {@linkplain Basket basket}
     * 
     * @param basket {@linkplain Basket basket} object to be created and saved
     * <br>
     * The id of the basket object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Basket basket} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Basket createBasket(Basket basket) throws IOException;

    /**
     * Updates and saves a {@linkplain Basket basket}
     * 
     * @param {@link Basket basket} object to be updated and saved
     * 
     * @return updated {@link Basket basket} if successful, null if
     * {@link Basket basket} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Basket updateBasket(Basket basket) throws IOException;

    /**
     * Deletes a {@linkplain Basket basket} with the given id
     * 
     * @param id The id of the {@link Basket basket}
     * 
     * @return true if the {@link Basket basket} was deleted
     * <br>
     * false if basket with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteBasket(int id) throws IOException;
}