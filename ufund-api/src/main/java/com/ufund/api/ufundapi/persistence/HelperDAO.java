package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Helper;
import com.ufund.api.ufundapi.model.Need;

/**
 * Interface for saving a helper to the data store
 * 
 */
public interface HelperDAO {
    /**
     * Retrieves all {@linkplain Helper helpers}
     * 
     * @return An array of {@link Helper helpers} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Helper[] getHelpers() throws IOException;

    /**
     * Retrieves an {@linkplain Helper helper} with the given username
     * 
     * @param username The username of the {@link Helper helper} to get
     * 
     * @return an {@link Helper helper} object with the matching username
     * <br>
     * null if no {@link Helper helper} with a matching username is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Helper getHelper(String username) throws IOException;

        /**
     * Logins to an {@linkplain Helper helper} with the given username and password
     * 
     * @param username The username of the {@link Helper helper} to login with
     * @param password The password of the {@link Helper helper} to login with
     * 
     * @return an {@link Helper helper} object with the matching username and password
     * <br>
     * null if no {@link Helper helper} with a matching username and password is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Helper loginHelper(String username, String password) throws IOException;

    /**
     * Creates and saves an {@linkplain Helper helper}
     * 
     * @param helper {@linkplain Helper helper} object to be created and saved
     * <br>
     *
     * @return new {@link Helper helper} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Helper createHelper(Helper helper) throws IOException;

    /**
     * Updates and saves an {@linkplain Helper helper}
     * 
     * @param {@link Helper helper} object to be updated and saved
     * 
     * @return updated {@link Helper helper} if successful, null if
     * {@link Helper helper} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Helper updateHelper(Helper helper) throws IOException;

    /**
     * Deletes an {@linkplain Helper helper} with the given username
     * 
     * @param username The username of the {@link Helper helper}
     * 
     * @return true if the {@link Helper helper} was deleted
     * <br>
     * false if helper with the given username does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteHelper(String username) throws IOException;

    /**
     * Checks out the {@linkplain Need}s in the Helper's basket.
     * @param username: the username of the Helper being checked out.
     * @return whether the checkout was successful.
     * @throws IOException if underlying storage can't be accessed
     */
    boolean checkoutBasket(String username) throws IOException;
}