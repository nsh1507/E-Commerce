package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import com.ufund.api.ufundapi.model.Feedback;

/** 
 * Definition of the interface for Feedback object persistence
 * 
 * @author Team BCNS
 */
public interface FeedbackDAO {
    /**
     * Retrieves all {@linkplain FEEDBACK feedbacks}
     * 
     * @return An array of {@link Feedback feedback} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage is found
     */
    Feedback[] getFeedbacks() throws IOException;

    /**
     * Creates and saves a {@linkplain Feedback feedback}
     * 
     * @param feedback {@linkplain Feedback feedback} object to be created and saved
     * <br>
     * The id of the Feedback object is ignored and a new uniqe id is assigned
     *
     * @return new {@link Feedback feedback} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Feedback createFeedback(Feedback feedback) throws IOException;

    /**
     * Updates and saves a {@linkplain Feedback feedback}
     * 
     * @param {@link Feedback feedback} object to be updated and saved
     * 
     * @return updated {@link Feedback feedback} if successful, null if unsuccessful
     * {@link Feedback feedback} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Feedback updateFeedback(Feedback feedback) throws IOException;

    /**
     * Deletes a {@linkplain Feedback feedback} with the given id
     * 
     * @param id The id of the {@link Feedback feedback}
     * 
     * @return true if the {@link Feedback feedback} was deleted
     * <br>
     * false if feedback with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteFeedback(int id) throws IOException;
}
