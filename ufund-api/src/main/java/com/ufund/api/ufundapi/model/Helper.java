package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Helper entity
 * 
 * @author Nam Huynh
 */
public class Helper {
    private static final Logger LOG = Logger.getLogger(Helper.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Helper [id=%d, name=%s, cart=%d, password=%s, admin=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("cart") private int cart;
    @JsonProperty("password") private String password;
    @JsonProperty("admin") private boolean admin;

    /**
     * @param id The id of the helper
     * @param cart The id of the cart of the helper 
     * @param password The password to access user account
     * @param name The nuser name of the helper
     * @param admin Is the user a admin or just the helper
     * 
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Helper(@JsonProperty("id") int id, @JsonProperty("name") String name,@JsonProperty("cart") int cart,
            @JsonProperty("password") String password, @JsonProperty("admin") boolean admin ) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.cart = cart;
        this.admin = admin;

    }

    /**
     * Retrieves the id of the helper
     * @return The id of the helper
     */
    public int getId() {return id;}

    /**
     * Sets the name of the helper - necessary for JSON object to Java object deserialization
     * @param name The name of the helper
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the helper
     * @return The name of the helper
     */
    public String getName() {return name;}

    /**
     * Sets the cart id of the helper - necessary for JSON object to Java object deserialization
     * @param cart The cart id of the helper
     */
    public void setCart(int cart) {this.cart = cart;}

    /**
     * Retrieves the cart id of the helper
     * @return The cost of the product
     */
    public int getCart() {return cart;}

    /**
     * Sets the password of the helper - necessary for JSON object to Java object deserialization
     * @param password The password of the helper
     */
    public void setPassword(String password) {this.password = password;}

    /**
     * Retrieves the password of the helper
     * @return The password of the helper
     */
    public String getPassword() {return password;}


    /**
     * Check whether the user is an admin or not
     * * @return whether the user is an admin or not
     */
    public boolean isAdmin() {return admin;}


    /**
     * {@inheritDoc}
     */

    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id, name, cart, password, admin);
    }
}