package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import com.ufund.api.ufundapi.model.Need;

public interface BasketDAO {
    
    /**
     * Get the contents of a funding basket.
     * @param id the id of the funding basket.
     * @return an array of the funding basket's contents.
     * @throws IOException if basket storage can't be accessed
     */
    Need[] getContents( int id ) throws IOException;

    /**
     * Searches the basket for Needs containing the keyword.
     * @param id the id of the funding basket.
     * @param name the keyword being searched
     * @return an array of Needs containing the keyword
     * @throws IOException if basket storage can't be accessed
     */
    Need[] searchBasket( int id, String name) throws IOException;

    /**
     * Gets a {@link Need need} from a Helper's funding basket
     * @param id the id of the funding basket
     * @param needid the id of the need
     * @return the need from the basket
     */
    Need getBasketNeed( int id, int needid );

    /**
     * Adds a {@link Need need} to a Helper's funding basket.
     * @param need the need being added
     * @param id the id of the basket
     * @return whether the add was successful
     * @throws IOException if basket storage can't be accessed
     */
    boolean addToBasket( int id, Need need ) throws IOException;

    /**
     * Removes a {@link Need need} from a Helper's funding basket.
     * @param id the id of the funding basket.
     * @param needid the id of the need to be removed.
     * @return whether the removal was successful
     * @throws IOException if basket storage can't be accessed
     */
    boolean removeFromBasket( int id, int needid) throws IOException;

    /**
     * Empties all {@link Need need} from a Helper's funding basket.
     * @param id the id of the funding basket.
     * @return whether the basket was successfully emptied.
     * @throws IOException if basket storage can't be accessed
     */
    boolean clearBasket( int id ) throws IOException;

    /**
     * Loads a Helper's basket from the underlying storage into Java objects
     * @param id the id of the basket being accessed
     * @throws IOException if basket storage can't be accessed
     */
    void updateBasket( int id ) throws IOException;
}