package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Helper;

/**
 * Interface for saving a helper to the data store
 * 
 */
public interface HelperDAO {
    /**
     * Retrieves all {@link Helper helper} from the data store
     * 
     * @return Array of {@link Helper helper} objects, array may be empty
     * @throws IOException If the data store cannot be accessed
     */
    Helper[] getHelpers() throws IOException;

    /**
     * Finds all {@link Helper helper} whose name matches given text
     * 
     * @param name Name to search for
     * @return Array of matching {@link Helper helper}, array may be empty
     * @throws IOException If the data store cannot be accessed
     */
    Helper[] findHelpers(String name) throws IOException;

    /**
     * Retrieves a {@link Helper helper} from the data store
     * 
     * @param id ID of the {@link Helper helper} to get
     * @return {@link Helper Helper} or null if not found
     * @throws IOException If the data store cannot be accessed
     */
    Helper getHelper(int id) throws IOException;

    /**
     * Saves a {@link Helper helper} to the data store
     * 
     * @param helper Created {@link Helper helper} to save
     * @return {@link Helper helper} (null if not found)
     * @throws IOException If the data store cannot be accessed
     */
    Helper createHelper(Helper helper) throws IOException;

    /**
     * Updates a {@link Helper helper} in the data store
     * 
     * @param helper Updated {@link Helper helper} to save
     * @return {@link Helper helper} (null if not found)
     * @throws IOException If the data store cannot be accessed
     */
    Helper updateHelpers(Helper helper) throws IOException;

    /**
     * Deletes a {@link Helper helper} from the data store
     * 
     * @param id ID of the {@link Helper helper} to delete
     * @return True if the ID helper was deleted false if not
     * @throws IOException If the data store cannot be accessed
     */
    boolean deleteHelper(int id) throws IOException;
}