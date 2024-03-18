package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents an Helper entity
 */
public class Helper {
    private static final Logger LOG = Logger.getLogger(Helper.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Helper [username=%s, password=%s, cart=%s]";

    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;

    /**
     * Create an helper with the given username and password
     * @param username The username of the helper
     * @param password The password of the helper
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Helper(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Retrieves the username of the helper
     * @return The username of the helper
     */
    public String getUsername() {return username;}

    /**
     * Retrieves the password of the helper
     * @return The password of the helper
     */
    public String getPassword() {return password;}

}