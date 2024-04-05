package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Feedback entity
 * 
 * @author Nam Huynh
 */
public class Feedback {
    private static final Logger LOG = Logger.getLogger(Feedback.class.getName());

    // Package private for tests
    public static final String STRING_FORMAT = "Feedback [id=%d, feedback=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("feedback") private String feedback;


    /**
     * @param id The id of the feedback
     * @param feedback The feedback from user
     * 
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Feedback(@JsonProperty("id") int id, @JsonProperty("feedback") String feedback) {
        this.id = id;
        this.feedback = feedback;
    }

    /**
     * Retrieves the id of the product
     * @return The id of the product
     */
    public int getId() {return id;}

    /**
     * Sets the content of the feedback - necessary for JSON object to Java object deserialization
     * @param feedback The feedback from user
     */
    public void setFeedback(String feedback) {this.feedback = feedback;}

    /**
     * Retrieves the feedback of the user
     * @return The feedback of the user
     */
    public String getFeedback() {return this.feedback;}


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id, feedback);
    }
}