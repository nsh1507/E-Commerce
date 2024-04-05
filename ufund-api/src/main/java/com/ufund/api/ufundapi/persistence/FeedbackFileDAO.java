package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ufund.api.ufundapi.model.Feedback;

@Component
public class FeedbackFileDAO implements FeedbackDAO{
    private static final Logger LOG = Logger.getLogger(FeedbackFileDAO.class.getName());
    Map<Integer,Feedback> feedbacks;   // Provides a local cache of the feedback objects
                                // so that we don't feedback to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Feedback
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new feedback
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Feedback File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public FeedbackFileDAO(@Value("${feedbacks.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the feedback from the file
    }

    /**
     * Generates the next id for a new {@linkplain Feedback feedback}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Feedback feedbacks} from the tree map
     * 
     * @return  The array of {@link Feedback feedbacks}, may be empty
     */
    private Feedback[] getFeedbacksArray() {
        return getFeedbacksArray(null);
    }

    /**
     * Generates an array of {@linkplain Feedback feedbacks} from the tree map for any
     * {@linkplain Feedback feedbacks} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Feedback feedbacks}
     * in the tree map
     * 
     * @return  The array of {@link Feedback feedbacks}, may be empty
     */
    private Feedback[] getFeedbacksArray(String containsText) { // if containsText == null, no filter
        ArrayList<Feedback> feedbackArrayList = new ArrayList<>();

        for (Feedback feedback : feedbacks.values()) {
                feedbackArrayList.add(feedback);
        }

        Feedback[] feedbackArray = new Feedback[feedbackArrayList.size()];
        feedbackArrayList.toArray(feedbackArray);
        return feedbackArray;
    }

    /**
     * Saves the {@linkplain Feedback feedbacks} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Feedback feedbacks} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Feedback[] feedbackArray = getFeedbacksArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),feedbackArray);
        return true;
    }

    /**
     * Loads {@linkplain Feedback feedback} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        feedbacks = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of feedbacks
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Feedback[] feedbackArray = objectMapper.readValue(new File(filename),Feedback[].class);

        // Add each feedback to the tree map and keep track of the greatest id
        for (Feedback feedback : feedbackArray) {
            feedbacks.put(feedback.getId(),feedback);
            if (feedback.getId() > nextId)
                nextId = feedback.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Feedback[] getFeedbacks() {
        synchronized(feedbacks) {
            return getFeedbacksArray();
        }
    }


    /**
    ** {@inheritDoc}
     */
    @Override
    public Feedback createFeedback(Feedback feedback) throws IOException {
        synchronized(feedbacks) {
            // We create a new feedback object because the id field is immutable
            // and we feedback to assign the next unique id
            Feedback newFeedback = new Feedback(nextId(), feedback.getFeedback());
            feedbacks.put(newFeedback.getId(),newFeedback);
            save(); // may throw an IOException
            return newFeedback;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Feedback updateFeedback(Feedback feedback) throws IOException {
        synchronized(feedbacks) {
            if (feedbacks.containsKey(feedback.getId()) == false)
                return null;  // feedback does not exist

            feedbacks.put(feedback.getId(),feedback);
            save(); // may throw an IOException
            return feedback;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteFeedback(int id) throws IOException {
        synchronized(feedbacks) {
            if (feedbacks.containsKey(id)) {
                feedbacks.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
